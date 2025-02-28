package lk.ac.iit.lecture_link.service.custom.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import lk.ac.iit.lecture_link.dto.LecturerDto;
import lk.ac.iit.lecture_link.dto.request.LecturerReqDto;
import lk.ac.iit.lecture_link.entity.Institute;
import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.entity.Picture;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.InstituteRepository;
import lk.ac.iit.lecture_link.repository.LecturerRepository;
import lk.ac.iit.lecture_link.repository.PictureRepository;
import lk.ac.iit.lecture_link.repository.SubjectRepository;
import lk.ac.iit.lecture_link.service.custom.LecturerService;
import lk.ac.iit.lecture_link.service.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final PictureRepository pictureRepository;
    private final SubjectRepository subjectRepository;
    private final InstituteRepository instituteRepository;
    private final Transformer transformer;
    private final Bucket bucket;

    private static final String INSTITUTE_NOT_FOUND_MSG = "No institute associated with the id";
    private static final String LECTURER_NOT_FOUND_MSG = "No lecturer associated with the id";

    @Override
    public LecturerDto saveLecturer(LecturerReqDto lecturerReqDto) {
        Lecturer lecturer = transformer.fromLecturerReqDto(lecturerReqDto);

        String signUrl = null;
        try {
            if (Objects.nonNull(lecturerReqDto.getPicture()) && lecturerReqDto.getPicture().getBytes().length > 0) {
                Picture picture = new Picture("lecturers/" + UUID.randomUUID());
                lecturer.setPicture(picture);
                Blob blobRef = null;
                blobRef = bucket.create(picture.getPicturePath(), lecturerReqDto.getPicture().getInputStream(),
                        lecturerReqDto.getPicture().getContentType());
                signUrl = (blobRef.signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
            } else {
                lecturer.setPicture(null);
            }
            lecturer = lecturerRepository.save(lecturer);
        }catch (IOException e) {
            throw new AppException(500, "Failed to upload the image", e);
        }

        LecturerDto lecturerDto = transformer.toLecturerDto(lecturer);
        lecturerDto.setPicture(signUrl);
        return lecturerDto;
    }


    @Override
    public void updateLecturerDetails(LecturerReqDto lecturerReqDto) {
        Lecturer currentLecturer = lecturerRepository.findById(lecturerReqDto.getId())
                .orElseThrow(() -> new AppException(404, LECTURER_NOT_FOUND_MSG));

        Lecturer newLecturer = transformer.fromLecturerReqDto(lecturerReqDto);
        newLecturer.setPicture(null);
        Blob blobRef = null;

        try {
            if (Objects.nonNull(lecturerReqDto.getPicture()) && lecturerReqDto.getPicture().getBytes().length > 0) {
                Picture picture = new Picture("lecturers/" + UUID.randomUUID());
                newLecturer.setPicture(picture);
                bucket.create(newLecturer.getPicture().getPicturePath(), lecturerReqDto.getPicture().getInputStream(),
                        lecturerReqDto.getPicture().getContentType());
                if(Objects.nonNull(currentLecturer.getPicture())){
                    blobRef = bucket.get(currentLecturer.getPicture().getPicturePath());
                    if(blobRef != null) {
                        blobRef.delete();
                    }
                    pictureRepository.deleteById(currentLecturer.getPicture().getId());
                }
            }else if(Objects.nonNull(currentLecturer.getPicture())){
                blobRef = bucket.get(currentLecturer.getPicture().getPicturePath());
                if(blobRef != null) {
                    blobRef.delete();
                }
                pictureRepository.deleteById(currentLecturer.getPicture().getId());
                newLecturer.setPicture(null);
            }
            lecturerRepository.save(newLecturer);
        } catch (IOException e) {
            throw new AppException(500, "Failed to update the image", e);
        }
    }

    @Override
    public void updateLecturerDetails(LecturerDto lecturerDto) {
        lecturerRepository.findById(lecturerDto.getId())
                .orElseThrow(() -> new AppException(404, LECTURER_NOT_FOUND_MSG));

        Lecturer newLecturer = transformer.fromLecturerDto(lecturerDto);

        lecturerRepository.save(newLecturer);
    }

    @Override
    public void deleteLecturer(Long lecturerId) {
        Lecturer currentLecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new AppException(404, LECTURER_NOT_FOUND_MSG));

        Blob blobRef = null;

        if (Objects.nonNull(currentLecturer.getPicture())) {
            blobRef = bucket.get(currentLecturer.getPicture().getPicturePath());
            pictureRepository.delete(currentLecturer.getPicture());
            if(blobRef != null && subjectRepository.findSubjectByLecturer(currentLecturer).isEmpty()) {
                blobRef.delete();
            }
        }

        lecturerRepository.deleteById(lecturerId);
    }

    @Override
    public LecturerDto getLecturer(Long lecturerId) {

        Optional<Lecturer> optLecturer = lecturerRepository.findById(lecturerId);
        if (optLecturer.isEmpty()) throw new AppException(404, LECTURER_NOT_FOUND_MSG);
        LecturerDto lecturerDto = transformer.toLecturerDto(optLecturer.get());

        if (Objects.nonNull(optLecturer.get().getPicture())) {
            lecturerDto.setPicture(bucket.get(optLecturer.get().getPicture().getPicturePath()).signUrl(1,
                    TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
        }
        return lecturerDto;
    }

    @Override
    public Set<LecturerDto> getLecturersForInstituteId(Long instituteId){
        Optional<Institute> optionalLecturer = instituteRepository.findById(instituteId);
        if (optionalLecturer.isEmpty()) throw new AppException(404, INSTITUTE_NOT_FOUND_MSG);

        Set<Lecturer> institutes = lecturerRepository.findLecturersByInstituteId(instituteId);
        return institutes.stream().map(transformer::toLecturerDto).collect(Collectors.toSet());
    }

    @Override
    public List<LecturerDto> getAllLecturers() {

        List<Lecturer> lecturerList = lecturerRepository.findAll();

        return lecturerList.stream().map(l -> {
            LecturerDto lecturerDto = transformer.toLecturerDto(l);
            if (Objects.nonNull(l.getPicture())) {
                lecturerDto.setPicture(bucket.get(l.getPicture().getPicturePath())
                        .signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
            }
            return lecturerDto;
        }).collect(Collectors.toList());

    }

    @Override
    public Page<LecturerDto> getFilteredLecturers(
            String district,
            BigDecimal payRateLower,
            BigDecimal payRateUpper,
            String qualification,
            Boolean isAssigned,
            String language,
            Pageable pageable) {

        Page<Lecturer> lecturerPage = lecturerRepository.findFilteredLecturers(
                district, payRateLower, payRateUpper, qualification, isAssigned, language, pageable);

        return lecturerPage.map(lecturer -> {
            LecturerDto lecturerDto = transformer.toLecturerDto(lecturer);

            if (Objects.nonNull(lecturer.getPicture())) {
                lecturerDto.setPicture(bucket.get(lecturer.getPicture().getPicturePath())
                        .signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
            }

            return lecturerDto;
        });
    }

    @Override
    public LecturerDto getLecturerByEmailAndPassword(String email, String password){
        Optional<Lecturer> optionalLecturer = lecturerRepository.getLecturerByEmailAndPassword(email, password);
        if (optionalLecturer.isEmpty()) throw new AppException(404, LECTURER_NOT_FOUND_MSG);

        return transformer.toLecturerDto(optionalLecturer.get());
    }

    @Override
    public LecturerDto findLecturerByEmail(String email){
        Optional<Lecturer> optionalLecturer = lecturerRepository.findLecturerByEmail(email);
        if (optionalLecturer.isEmpty()) throw new AppException(404, LECTURER_NOT_FOUND_MSG);

        return transformer.toLecturerDto(optionalLecturer.get());
    }

}
