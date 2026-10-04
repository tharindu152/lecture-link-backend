package lk.ac.iit.lecturer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record AiMatchRequestDto(
        String program,
        @JsonProperty("hourly_pay") BigDecimal hourlyPay,
        String level,
        @JsonProperty("time_pref") String timePreference,
        @JsonProperty("student_count") Integer studentCount,
        String subject,
        Integer credits,
        @JsonProperty("institute_rating") BigDecimal instituteRating,
        Integer duration,
        String division,
        String status,
        String language) {
}
