package lk.ac.iit.lecturer.dto;

import lk.ac.iit.lecturer.enums.Language;
import lk.ac.iit.lecturer.enums.LecturerStatus;
import lk.ac.iit.lecturer.enums.TimePreference;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LecturerDto(
        Long id,
        String name,
        String division,
        String mapsLocation,
        String email,
        String contactNo,
        Integer currentRating,
        Integer ratingsReceived,
        BigDecimal hourlyPayRate,
        String preference,
        LecturerStatus status,
        Boolean isAssigned,
        Language language,
        Integer lecturingExperience,
        String fieldOfWork,
        TimePreference timePreference,
        boolean subscribed,
        Long instituteId,
        String pictureUrl,
        LocalDateTime createdOn,
        LocalDateTime updatedOn) {
}
