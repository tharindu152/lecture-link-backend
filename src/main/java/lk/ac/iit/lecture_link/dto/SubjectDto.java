package lk.ac.iit.lecture_link.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto implements Serializable {
  private long id;
  @NotBlank(message = "Subject name can't be empty")
  @Length(max = 255, message = "Subject name must not exceed 255 characters")
  private String name;
  @Positive(message = "Number of credits must be positive")
  @NotNull(message = "Number of credits can not be null")
  @Min(1)
  @Max(4)
  private int noOfCredits;
  @Length(max = 1000, message = "Description must not exceed 1000 characters")
  private String description;
  @NotNull(message = "isAssigned can not be null")
  private Boolean isAssigned;
  private Long lecturerId;
}
