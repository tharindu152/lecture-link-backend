package lk.ac.iit.lecture_link.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDto implements Serializable {
  private long id;
  @NotBlank(message = "Program name can't be empty")
  @Length(max = 255, message = "Program name must not exceed 255 characters")
  private String name;
  @Length(max = 1000, message = "Description must not exceed 1000 characters")
  private String description;
  @NotBlank(message = "Level can't be empty")
  @Pattern(regexp = "^(DOCTORATE|MASTERS|POSTGRADUATE|BACHELORS|HND|HNC)$", message = "Level must be one of: DOCTORATE, MASTERS, POSTGRADUATE, BACHELORS, HND, HNC")
  private String level;
  @Positive(message = "Duration in days must be positive")
  private int durationInDays;
  @Positive(message = "Student count must be positive")
  private int studentCount;
  @Pattern(regexp = "^(ENGLISH|SINHALA|TAMIL)$", message = "Language must be 'ENGLISH', 'SINHALA' or 'TAMIL'")
  private String language;
  @Length(max = 255, message = "Batch ID must not exceed 255 characters")
  private String batchId;
  @NotNull(message = "Payment can't be null")
  @DecimalMin(value = "0.0", inclusive = false, message = "Payment must be greater than 0")
  private BigDecimal hourlyPayRate;
  @NotNull(message = "InstituteId can't be null")
  private Long instituteId;
  @Pattern(regexp = "^(WEEKEND|WEEKDAY|FLEXIBLE)$", message = "Time preference must be 'WEEKEND', 'WEEKDAY' or 'FLEXIBLE'")
  private String timePreference;
  private Set<SubjectDto> subjects;
}
