package lk.ac.iit.lecture_link.service.custom.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import lk.ac.iit.lecture_link.dto.InstituteDto;
import lk.ac.iit.lecture_link.dto.request.InstituteReqDto;
import lk.ac.iit.lecture_link.entity.Institute;
import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.entity.Logo;
import lk.ac.iit.lecture_link.enums.Status;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.InstituteRepository;
import lk.ac.iit.lecture_link.repository.LecturerRepository;
import lk.ac.iit.lecture_link.repository.LogoRepository;
import lk.ac.iit.lecture_link.repository.SubjectRepository;
import lk.ac.iit.lecture_link.service.custom.InstituteService;
import lk.ac.iit.lecture_link.service.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InstituteServiceImpl implements InstituteService {

    private final InstituteRepository instituteRepository;
    private final LogoRepository logoRepository;
    private final LecturerRepository lecturerRepository;
    private final SubjectRepository subjectRepository;
    private final Transformer transformer;
    private final Bucket bucket;

    private static final String INSTITUTE_NOT_FOUND_MSG = "No institute associated with the id";
    private static final String LECTURER_NOT_FOUND_MSG = "No lecturer associated with the id";
    private static final String SUBJECT_NOT_FOUND_MSG = "No subject associated with the id";

    @Override
    public InstituteDto saveInstitute(InstituteReqDto instituteReqDto) {
        Institute institute = transformer.fromInstituteReqDto(instituteReqDto);

        String signUrl = null;
        try {
            if (Objects.nonNull(instituteReqDto.getLogo()) && instituteReqDto.getLogo().getBytes().length > 0) {
                Logo logo = new Logo("institutes/" + UUID.randomUUID());
                institute.setLogo(logo);
                Blob blobRef = null;
                blobRef = bucket.create(logo.getLogoPath(), instituteReqDto.getLogo().getInputStream(),
                        instituteReqDto.getLogo().getContentType());
                signUrl = (blobRef.signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
            } else {
                institute.setLogo(null);
            }
            institute = instituteRepository.save(institute);
        }catch (IOException e) {
            throw new AppException(500, "Failed to upload the image", e);
        }

        InstituteDto instituteDto = transformer.toInstituteDto(institute);
        instituteDto.setLogo(signUrl);
        return instituteDto;
    }


    @Override
    public void updateInstituteDetails(InstituteReqDto instituteReqDto) {
        Institute currentInstitute = instituteRepository.findById(instituteReqDto.getId())
                .orElseThrow(() -> new AppException(404, INSTITUTE_NOT_FOUND_MSG));

        Institute newInstitute = transformer.fromInstituteReqDto(instituteReqDto);
        newInstitute.setLogo(null);
        Blob blobRef = null;

        try {
            if (Objects.nonNull(instituteReqDto.getLogo()) && instituteReqDto.getLogo().getBytes().length > 0) {
                Logo logo = new Logo("institutes/" + UUID.randomUUID());
                newInstitute.setLogo(logo);
                bucket.create(newInstitute.getLogo().getLogoPath(), instituteReqDto.getLogo().getInputStream(),
                        instituteReqDto.getLogo().getContentType());
                if(Objects.nonNull(currentInstitute.getLogo())){
                    blobRef = bucket.get(currentInstitute.getLogo().getLogoPath());
                    if(blobRef != null) {
                        blobRef.delete();
                    }
                    logoRepository.deleteById(currentInstitute.getLogo().getId());
                }
            }else if(Objects.nonNull(currentInstitute.getLogo())){
                blobRef = bucket.get(currentInstitute.getLogo().getLogoPath());
                if(blobRef != null) {
                    blobRef.delete();
                }
                logoRepository.deleteById(currentInstitute.getLogo().getId());
                newInstitute.setLogo(null);
            }
            instituteRepository.save(newInstitute);
        } catch (IOException e) {
            throw new AppException(500, "Failed to update the image", e);
        }
    }

    @Override
    public void updateInstituteDetails(InstituteDto instituteDto) {
        instituteRepository.findById(instituteDto.getId())
                .orElseThrow(() -> new AppException(404, INSTITUTE_NOT_FOUND_MSG));

        Institute newInstitute = transformer.fromInstituteDto(instituteDto);

        instituteRepository.save(newInstitute);
    }

    @Override
    public void deleteInstitute(Long instituteId) {
        Institute currentInstitute = instituteRepository.findById(instituteId)
                .orElseThrow(() -> new AppException(404, INSTITUTE_NOT_FOUND_MSG));

        Blob blobRef = null;

        try {
            if (Objects.nonNull(currentInstitute.getLogo())) {
                blobRef = bucket.get(currentInstitute.getLogo().getLogoPath());
                logoRepository.delete(currentInstitute.getLogo());
                blobRef.delete();
            }
        } catch (Exception e) {
            throw new AppException(500, "Failed to delete the image", e);
        }

        instituteRepository.deleteById(instituteId);
    }

    @Override
    public InstituteDto getInstitute(Long instituteId) {

        Optional<Institute> optInstitute = instituteRepository.findById(instituteId);
        if (optInstitute.isEmpty()) throw new AppException(404, INSTITUTE_NOT_FOUND_MSG);
        InstituteDto instituteDto = transformer.toInstituteDto(optInstitute.get());

        try {
            if (Objects.nonNull(optInstitute.get().getLogo())) {
                instituteDto.setLogo(bucket.get(optInstitute.get().getLogo().getLogoPath()).signUrl(1,
                        TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
            }
        } catch (Exception e) {
            throw new AppException(500, "Failed to retrieve the image", e);
        }

        return instituteDto;
    }

    @Override
    public List<InstituteDto> getAllInstitutes() {
        List<Institute> instituteList = instituteRepository.findAll();

        try {
            return instituteList.stream().map(l -> {
                InstituteDto instituteDto = transformer.toInstituteDto(l);
                if (Objects.nonNull(l.getLogo())) {
                    instituteDto.setLogo(bucket.get(l.getLogo().getLogoPath())
                            .signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
                }
                return instituteDto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new AppException(500, "Failed to retrieve the image", e);
        }
    }

    @Override
    public Set<InstituteDto> getInstitutesForLecturerId(Long lecturerId){
        Optional<Lecturer> optionalLecturer = lecturerRepository.findById(lecturerId);
        if (optionalLecturer.isEmpty()) throw new AppException(404, LECTURER_NOT_FOUND_MSG);

        Set<Institute> instituteList = instituteRepository.findInstitutesByLecturerId(lecturerId);
        try {
            return instituteList.stream().map(l -> {
                InstituteDto instituteDto = transformer.toInstituteDto(l);
                if (Objects.nonNull(l.getLogo())) {
                    instituteDto.setLogo(bucket.get(l.getLogo().getLogoPath())
                            .signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
                }
                return instituteDto;
            }).collect(Collectors.toSet());
        } catch (Exception e) {
            throw new AppException(500, "Failed to retrieve the image", e);
        }
    }

    @Override
    public Page<InstituteDto> getFilteredInstitutes(String district, String status, Pageable pageable) {
        Page<Institute> institutePage = instituteRepository.findFilteredInstitutes(district, status, pageable);
        try {
            return institutePage.map(institute -> {
                InstituteDto instituteDto = transformer.toInstituteDto(institute);
                if (Objects.nonNull(institute.getLogo())) {
                    instituteDto.setLogo(bucket.get(institute.getLogo().getLogoPath())
                            .signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
                }
                return instituteDto;
            });
        } catch (Exception e) {
            throw new AppException(500, "Failed to retrieve the image", e);
        }
    }

    @Override
    public InstituteDto getInstituteByEmailAndPassword(String email, String password){
        Optional<Institute> optionalInstitute = instituteRepository.getInstituteByEmailAndPassword(email, password);
        if (optionalInstitute.isEmpty()) throw new AppException(404, LECTURER_NOT_FOUND_MSG);

        return transformer.toInstituteDto(optionalInstitute.get());
    }

    @Override
    public InstituteDto findInstituteByEmail(String email){
        Optional<Institute> optionalInstitute = instituteRepository.findInstituteByEmail(email);
        if (optionalInstitute.isEmpty()) throw new AppException(404, INSTITUTE_NOT_FOUND_MSG);

        return transformer.toInstituteDto(optionalInstitute.get());
    }

    public void updateInstituteRating(Long instituteId, int newRating) {
        Institute institute = instituteRepository.findById(instituteId)
                .orElseThrow(() -> new AppException(404, INSTITUTE_NOT_FOUND_MSG));

        int totalRatings = institute.getRatingsReceived() + 1;
        double newAverageRating = ((institute.getCurrentRating() * institute.getRatingsReceived()) + newRating) / (double) totalRatings;

        institute.setCurrentRating((int) Math.round(newAverageRating));
        institute.setRatingsReceived(totalRatings);

        instituteRepository.save(institute);
    }

    public void deactivateInstitute(Long instituteId) {
        Institute institute = instituteRepository.findById(instituteId)
                .orElseThrow(() -> new AppException(404, INSTITUTE_NOT_FOUND_MSG));

        institute.setStatus(Status.INACTIVE.getStatus());
        instituteRepository.save(institute);
    }

    @Override
    public void updateInstituteSubscription(Long instituteId, boolean subscribed) {
        Institute institute = instituteRepository.findById(instituteId)
                .orElseThrow(() -> new AppException(404, "Institute not found"));
        institute.setSubscribed(subscribed);
        instituteRepository.save(institute);
    }

    @Override
    public String getInstituteEmailBySubjectId(Long subjectId) {
        subjectRepository.findById(subjectId)
                .orElseThrow(() -> new AppException(404, SUBJECT_NOT_FOUND_MSG));
        return instituteRepository.findInstituteEmailBySubjectId(subjectId);
    }
}
