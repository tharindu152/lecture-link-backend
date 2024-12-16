package lk.ac.iit.lecture_link.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
}
