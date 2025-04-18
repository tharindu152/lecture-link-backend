package lk.ac.iit.lecture_link.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "program",
        "hourly_pay",
        "level",
        "time_pref",
        "student_count",
        "subject",
        "credits",
        "institute_rating"
})
public class AiMatchRequestDto {
    @Length(max = 255, message = "Program name must not exceed 255 characters")
    private String program;
    @DecimalMin(value = "0.0", inclusive = false, message = "Pay rate must be greater than 0")
    @JsonProperty("hourly_pay")
    private BigDecimal hourlyPay;
    @NotBlank(message = "Level can't be empty")
    @Pattern(regexp = "^(DOCTORATE|MASTERS|POSTGRADUATE|BACHELORS|HND|HNC)$", message = "Level must be one of: DOCTORATE, MASTERS, POSTGRADUATE, BACHELORS, HND, HNC")
    private String level;
    @Pattern(regexp = "^(WEEKEND|WEEKDAY|FLEXIBLE)$", message = "Time preference must be 'WEEKEND', 'WEEKDAY' or 'FLEXIBLE'")
    @JsonProperty("time_pref")
    private String timePreference;
    @Positive(message = "Student count must be positive")
    @JsonProperty("student_count")
    private int studentCount;
    @NotBlank(message = "Subject name can't be empty")
    @Length(max = 255, message = "Subject name must not exceed 255 characters")
    private String subject;
    @Positive(message = "Number of credits must be positive")
    @NotNull(message = "Number of credits can not be null")
    @Min(1)
    @Max(4)
    @JsonProperty("credits")
    private int noOfCredits;
    @Min(0)
    @Max(5)
    @JsonProperty("institute_rating")
    private double instituteRating;
}