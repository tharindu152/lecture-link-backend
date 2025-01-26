package lk.ac.iit.lecture_link.dto;

import lk.ac.iit.lecture_link.entity.Subject;
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
  @Pattern(regexp = "^(MSC|BSC|HND|PGD)$", message = "Level must be one of: MSC, BSC, HND, PGD")
  private String level;
  @Positive(message = "Duration in days must be positive")
  private int durationInDays;
  @Positive(message = "Student count must be positive")
  private int studentCount;
  @Length(max = 255, message = "Batch ID must not exceed 255 characters")
  private String batchId;
  @NotNull(message = "Payment can't be null")
  @DecimalMin(value = "0.0", inclusive = false, message = "Payment must be greater than 0")
  private BigDecimal payment;
  @NotNull(message = "InstituteId can't be null")
  private Long instituteId;
  private Set<Subject> subjects;
}
