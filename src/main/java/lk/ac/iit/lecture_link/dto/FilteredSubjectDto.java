package lk.ac.iit.lecture_link.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilteredSubjectDto implements Serializable {
  private long id;
  @NotBlank(message = "Subject name can't be empty")
  @Length(max = 255, message = "Subject name must not exceed 255 characters")
  private String name;
  @Pattern(regexp = "^(MSC|BSC|HND|PGD|PHD)$", message = "Level must be one of: PHD, MSC, BSC, HND, PGD")
  private String level;
  @Positive(message = "Number of credits must be positive")
  @NotNull(message = "Number of credits can not be null")
  @Min(1)
  @Max(4)
  private int noOfCredits;
  @Positive(message = "Duration in days must be positive")
  private int durationInDays;
  @Positive(message = "Student count must be positive")
  private int studentCount;
  @NotBlank(message = "District can't be empty")
  @Length(max = 500, message = "District must not exceed 500 characters")
  private String district;
  @NotNull(message = "Payment can't be null")
  @DecimalMin(value = "0.0", inclusive = false, message = "Payment must be greater than 0")
  private BigDecimal payment;
}
