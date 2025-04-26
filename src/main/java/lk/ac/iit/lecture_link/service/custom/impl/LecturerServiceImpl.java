package lk.ac.iit.lecture_link.service.custom.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import lk.ac.iit.lecture_link.dto.LecturerDto;
import lk.ac.iit.lecture_link.dto.request.LecturerReqDto;
import lk.ac.iit.lecture_link.entity.Institute;
import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.entity.Picture;
import lk.ac.iit.lecture_link.enums.Status;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.InstituteRepository;
import lk.ac.iit.lecture_link.repository.LecturerRepository;
import lk.ac.iit.lecture_link.repository.PictureRepository;
import lk.ac.iit.lecture_link.repository.SubjectRepository;
import lk.ac.iit.lecture_link.service.custom.LecturerService;
import lk.ac.iit.lecture_link.service.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(LecturerServiceImpl.class);

    private final LecturerRepository lecturerRepository;
    private final PictureRepository pictureRepository;
    private final SubjectRepository subjectRepository;
    private final InstituteRepository instituteRepository;
    private final Transformer transformer;
    private final Bucket bucket;

    private static final String INSTITUTE_NOT_FOUND_MSG = "No institute associated with the id";
    private static final String LECTURER_NOT_FOUND_MSG = "No lecturer associated with the id";
    private static final String FAILED_TO_RETRIEVE_IMAGE = "Failed to retrieve the image";

    @Override
    public LecturerDto saveLecturer(LecturerReqDto lecturerReqDto) {
        log.info("Saving lecturer with name: {}", lecturerReqDto.getName());
        Lecturer lecturer = transformer.fromLecturerReqDto(lecturerReqDto);

        String signUrl = null;
        try {
            if (Objects.nonNull(lecturerReqDto.getPicture()) && lecturerReqDto.getPicture().getBytes().length > 0) {
                log.info("Uploading picture for lecturer: {}", lecturerReqDto.getName());
                Picture picture = new Picture("lecturers/" + UUID.randomUUID());
                lecturer.setPicture(picture);
                Blob blobRef = bucket.create(picture.getPicturePath(), lecturerReqDto.getPicture().getInputStream(),
                        lecturerReqDto.getPicture().getContentType());
                signUrl = blobRef.signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString();
            } else {
                log.warn("No picture provided for lecturer: {}", lecturerReqDto.getName());
                lecturer.setPicture(null);
            }
            lecturer = lecturerRepository.save(lecturer);
            log.info("Lecturer saved successfully with ID: {}", lecturer.getId());
        } catch (IOException e) {
            throw new AppException(500, "Failed to upload the image", e);
        }

        LecturerDto lecturerDto = transformer.toLecturerDto(lecturer);
        lecturerDto.setPicture(signUrl);
        return lecturerDto;
    }

    @Override
    public void updateLecturerDetails(LecturerReqDto lecturerReqDto) {
        log.info("Updating details for lecturer with ID: {}", lecturerReqDto.getId());
        Lecturer currentLecturer = lecturerRepository.findById(lecturerReqDto.getId())
                .orElseThrow(() -> {
                    log.error(LECTURER_NOT_FOUND_MSG + ": {}", lecturerReqDto.getId());
                    return new AppException(404, LECTURER_NOT_FOUND_MSG);
                });

        Lecturer newLecturer = transformer.fromLecturerReqDto(lecturerReqDto);
        newLecturer.setPicture(null);
        Blob blobRef = null;

        try {
            if (Objects.nonNull(lecturerReqDto.getPicture()) && lecturerReqDto.getPicture().getBytes().length > 0) {
                log.info("Updating picture for lecturer with ID: {}", lecturerReqDto.getId());
                Picture picture = new Picture("lecturers/" + UUID.randomUUID());
                newLecturer.setPicture(picture);
                bucket.create(newLecturer.getPicture().getPicturePath(), lecturerReqDto.getPicture().getInputStream(),
                        lecturerReqDto.getPicture().getContentType());
                if (Objects.nonNull(currentLecturer.getPicture())) {
                    blobRef = bucket.get(currentLecturer.getPicture().getPicturePath());
                    if (blobRef != null) {
                        blobRef.delete();
                    }
                    pictureRepository.deleteById(currentLecturer.getPicture().getId());
                }
            } else if (Objects.nonNull(currentLecturer.getPicture())) {
                log.warn("Removing existing picture for lecturer with ID: {}", lecturerReqDto.getId());
                blobRef = bucket.get(currentLecturer.getPicture().getPicturePath());
                if (blobRef != null) {
                    blobRef.delete();
                }
                pictureRepository.deleteById(currentLecturer.getPicture().getId());
                newLecturer.setPicture(null);
            }
            lecturerRepository.save(newLecturer);
            log.info("Lecturer details updated successfully for ID: {}", lecturerReqDto.getId());
        } catch (IOException e) {
            throw new AppException(500, "Failed to update the image", e);
        }
    }

    @Override
    public void updateLecturerDetails(LecturerDto lecturerDto) {
        log.info("Updating lecturer with ID: {}", lecturerDto.getId());
        lecturerRepository.findById(lecturerDto.getId())
                .orElseThrow(() -> {
                    log.error(LECTURER_NOT_FOUND_MSG + ": {}", lecturerDto.getId());
                    return new AppException(404, LECTURER_NOT_FOUND_MSG);
                });

        Lecturer newLecturer = transformer.fromLecturerDto(lecturerDto);
        lecturerRepository.save(newLecturer);
        log.info("Lecturer updated successfully with ID: {}", lecturerDto.getId());
    }

    @Override
    public void deleteLecturer(Long lecturerId) {
        log.info("Deleting lecturer with ID: {}", lecturerId);
        Lecturer currentLecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> {
                    log.error(LECTURER_NOT_FOUND_MSG + ": {}", lecturerId);
                    return new AppException(404, LECTURER_NOT_FOUND_MSG);
                });

        Blob blobRef = null;

        try {
            if (Objects.nonNull(currentLecturer.getPicture())) {
                log.info("Deleting picture for lecturer with ID: {}", lecturerId);
                blobRef = bucket.get(currentLecturer.getPicture().getPicturePath());
                pictureRepository.delete(currentLecturer.getPicture());
                if (blobRef != null && subjectRepository.findSubjectByLecturer(currentLecturer).isEmpty()) {
                    blobRef.delete();
                }
            }
        } catch (Exception e) {
            throw new AppException(500, "Failed to delete the image", e);
        }

        lecturerRepository.deleteById(lecturerId);
        log.info("Lecturer deleted successfully with ID: {}", lecturerId);
    }

    @Override
    public LecturerDto getLecturer(Long lecturerId) {
        log.info("Fetching lecturer with ID: {}", lecturerId);
        Optional<Lecturer> optLecturer = lecturerRepository.findById(lecturerId);
        if (optLecturer.isEmpty()) {
            log.error(LECTURER_NOT_FOUND_MSG + ": {}", lecturerId);
            throw new AppException(404, LECTURER_NOT_FOUND_MSG);
        }
        LecturerDto lecturerDto = transformer.toLecturerDto(optLecturer.get());

        try {
            if (Objects.nonNull(optLecturer.get().getPicture())) {
                log.info("Fetching picture for lecturer with ID: {}", lecturerId);
                lecturerDto.setPicture(bucket.get(optLecturer.get().getPicture().getPicturePath())
                        .signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
            }
            return lecturerDto;
        } catch (Exception e) {
            throw new AppException(500, FAILED_TO_RETRIEVE_IMAGE, e);
        }
    }

    @Override
    public Set<LecturerDto> getLecturersForInstituteId(Long instituteId) {
        log.info("Fetching lecturers for institute with ID: {}", instituteId);
        Optional<Institute> optionalInstitute = instituteRepository.findById(instituteId);
        if (optionalInstitute.isEmpty()) {
            log.error(INSTITUTE_NOT_FOUND_MSG + ": {}", instituteId);
            throw new AppException(404, INSTITUTE_NOT_FOUND_MSG);
        }

        Set<Lecturer> lecturerList = lecturerRepository.findLecturersByInstituteId(instituteId);

        try {
            return lecturerList.stream().map(l -> {
                LecturerDto lecturerDto = transformer.toLecturerDto(l);
                if (Objects.nonNull(l.getPicture())) {
                    lecturerDto.setPicture(bucket.get(l.getPicture().getPicturePath())
                            .signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
                }
                return lecturerDto;
            }).collect(Collectors.toSet());
        } catch (Exception e) {
            throw new AppException(500, FAILED_TO_RETRIEVE_IMAGE, e);
        }
    }

    @Override
    public List<LecturerDto> getAllLecturers() {
        log.info("Fetching all lecturers");
        List<Lecturer> lecturerList = lecturerRepository.findAll();

        try {
            return lecturerList.stream().map(l -> {
                LecturerDto lecturerDto = transformer.toLecturerDto(l);
                if (Objects.nonNull(l.getPicture())) {
                    lecturerDto.setPicture(bucket.get(l.getPicture().getPicturePath())
                            .signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
                }
                return lecturerDto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new AppException(500, FAILED_TO_RETRIEVE_IMAGE, e);
        }
    }

    @Override
    public Page<LecturerDto> getFilteredLecturers(
            String division,
            BigDecimal payRateLower,
            BigDecimal payRateUpper,
            String qualification,
            Boolean isAssigned,
            String language,
            String globalSearch,
            Pageable pageable) {
        log.info("Fetching filtered lecturers with criteria: division={}, payRateLower={}, payRateUpper={}, qualification={}, isAssigned={}, language={}, globalSearch={}",
                division, payRateLower, payRateUpper, qualification, isAssigned, language, globalSearch);

        Page<Lecturer> lecturerPage = lecturerRepository.findFilteredLecturers(
                division, payRateLower, payRateUpper, qualification, isAssigned, language, globalSearch, pageable);

        try {
            return lecturerPage.map(lecturer -> {
                LecturerDto lecturerDto = transformer.toLecturerDto(lecturer);

                if (Objects.nonNull(lecturer.getPicture())) {
                    lecturerDto.setPicture(bucket.get(lecturer.getPicture().getPicturePath())
                            .signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
                }

                return lecturerDto;
            });
        } catch (Exception e) {
            throw new AppException(500, FAILED_TO_RETRIEVE_IMAGE, e);
        }
    }

    @Override
    public LecturerDto getLecturerByEmailAndPassword(String email, String password) {
        log.info("Fetching lecturer by email: {}", email);
        Optional<Lecturer> optionalLecturer = lecturerRepository.getLecturerByEmailAndPassword(email, password);
        if (optionalLecturer.isEmpty()) {
            log.error(LECTURER_NOT_FOUND_MSG + " for email: {}", email);
            throw new AppException(404, LECTURER_NOT_FOUND_MSG);
        } else if(optionalLecturer.get().getStatus().equals(Status.INACTIVE.getStatus())) {
            log.error("Lecturer is inactive for email: {}", email);
            throw new AppException(403, "Lecturer is inactive");
        }

        return transformer.toLecturerDto(optionalLecturer.get());
    }

    @Override
    public LecturerDto findLecturerByEmail(String email) {
        log.info("Fetching lecturer by email: {}", email);
        Optional<Lecturer> optionalLecturer = lecturerRepository.findLecturerByEmail(email);
        if (optionalLecturer.isEmpty()) {
            log.error(LECTURER_NOT_FOUND_MSG + " for email: {}", email);
            throw new AppException(404, LECTURER_NOT_FOUND_MSG);
        }

        return transformer.toLecturerDto(optionalLecturer.get());
    }

    public void updateLecturerRating(Long lecturerId, int newRating) {
        log.info("Updating rating for lecturer with ID: {}", lecturerId);
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> {
                    log.error(LECTURER_NOT_FOUND_MSG + ": {}", lecturerId);
                    return new AppException(404, LECTURER_NOT_FOUND_MSG);
                });

        int totalRatings = lecturer.getRatingsReceived() + 1;
        double newAverageRating = ((lecturer.getCurrentRating() * lecturer.getRatingsReceived()) + newRating) / (double) totalRatings;

        lecturer.setCurrentRating((int) Math.round(newAverageRating));
        lecturer.setRatingsReceived(totalRatings);

        lecturerRepository.save(lecturer);
        log.info("Rating updated successfully for lecturer with ID: {}", lecturerId);
    }

    public void deactivateLecturer(Long lecturerId) {
        log.info("Deactivating lecturer with ID: {}", lecturerId);
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> {
                    log.error(LECTURER_NOT_FOUND_MSG + ": {}", lecturerId);
                    return new AppException(404, LECTURER_NOT_FOUND_MSG);
                });

        lecturer.setStatus(Status.INACTIVE.getStatus());
        lecturerRepository.save(lecturer);
        log.info("Lecturer deactivated successfully with ID: {}", lecturerId);
    }

    @Override
    public void updateLecturerSubscription(Long lecturerId, boolean subscribed) {
        log.info("Updating subscription status for lecturer with ID: {}", lecturerId);
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> {
                    log.error("Lecturer not found with ID: {}", lecturerId);
                    return new AppException(404, "Lecturer not found");
                });
        lecturer.setSubscribed(subscribed);
        lecturerRepository.save(lecturer);
        log.info("Subscription status updated successfully for lecturer with ID: {}", lecturerId);
    }

    @Override
    public void updateLecturerIsAssigned(Long lecturerId, boolean isAssigned) {
        log.info("Updating isAssigned status for lecturer with ID: {}", lecturerId);
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> {
                    log.error("Lecturer not found with ID: {}", lecturerId);
                    return new AppException(404, "Lecturer not found");
                });
        lecturer.setIsAssigned(isAssigned);
        lecturerRepository.save(lecturer);
        log.info("Assignment status updated successfully for lecturer with ID: {}", lecturerId);
    }

}