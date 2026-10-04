package lk.ac.iit.lecturer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record LecturerRequest(
        @NotBlank String name,
        @NotBlank String division,
        String mapsLocation,
        @NotBlank @Email String email,
        String contactNo,
        BigDecimal hourlyPayRate,
        String preference,
        String language,
        Integer lecturingExperience,
        String fieldOfWork,
        String timePreference,
        Long instituteId,
        String pictureUrl,
        Integer currentRating,
        Integer ratingsReceived,
        Boolean isAssigned,
        Boolean subscribed,
        String status) {
}
