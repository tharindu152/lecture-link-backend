package lk.ac.iit.lecturer.util;

import lk.ac.iit.lecturer.dto.LecturerDto;
import lk.ac.iit.lecturer.dto.QualificationDto;
import lk.ac.iit.lecturer.entity.Lecturer;
import lk.ac.iit.lecturer.entity.Qualification;
import lk.ac.iit.lecturer.enums.Language;
import lk.ac.iit.lecturer.enums.LecturerStatus;
import lk.ac.iit.lecturer.enums.QualificationLevel;
import lk.ac.iit.lecturer.enums.TimePreference;
import org.springframework.stereotype.Component;

@Component
public class LecturerMapper {
    private final ImageStorageService imageStorageService;

    public LecturerMapper(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    public LecturerDto toDto(Lecturer entity) {
        return new LecturerDto(entity.getId(), entity.getName(), entity.getDivision(), entity.getMapsLocation(),
                entity.getEmail(), entity.getContactNo(), entity.getCurrentRating(), entity.getRatingsReceived(),
                entity.getHourlyPayRate(), entity.getPreference(), entity.getStatus(), entity.getIsAssigned(),
                entity.getLanguage(), entity.getLecturingExperience(), entity.getFieldOfWork(),
                entity.getTimePreference(), entity.isSubscribed(), entity.getInstituteId(),
                imageStorageService.getUrl(entity.getPictureUrl()),
                entity.getCreatedOn(), entity.getUpdatedOn());
    }

    public QualificationDto toDto(Qualification entity) {
        return new QualificationDto(entity.getId(), entity.getName(), entity.getAwardingBody(),
                entity.getDurationInDays(), entity.getDiscipline(), entity.getCompletedAt(), entity.getLevel(),
                entity.getLecturerId(), entity.getCreatedOn(), entity.getUpdatedOn());
    }

    public Language language(String value) {
        return value == null || value.isBlank() ? null : Language.valueOf(value.toUpperCase());
    }

    public TimePreference timePreference(String value) {
        return value == null || value.isBlank() ? null : TimePreference.valueOf(value.toUpperCase());
    }

    public QualificationLevel qualificationLevel(String value) {
        return value == null || value.isBlank() ? null : QualificationLevel.valueOf(value.toUpperCase());
    }

    public LecturerStatus lecturerStatus(String value) {
        return value == null || value.isBlank() ? LecturerStatus.ACTIVE : LecturerStatus.valueOf(value.toUpperCase());
    }
}
