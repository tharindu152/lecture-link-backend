package lk.ac.iit.lecture_link.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualificationDto implements Serializable {
  private long id;
  @NotBlank(message = "Qualification name can't be empty")
  @Length(max = 255, message = "Qualification name must not exceed 255 characters")
  private String name;
  @NotBlank(message = "Awarding body can't be empty")
  @Length(max = 255, message = "Awarding body must not exceed 255 characters")
  private String awardingBody;
  @Positive(message = "Duration in days must be positive")
  private int durationInDays;
  @Length(max = 255, message = "Discipline must not exceed 255 characters")
  private String discipline;
  @NotNull(message = "Completion date can't be null")
  private String completedAt;
  @NotBlank(message = "Level can't be empty")
  @Pattern(regexp = "^(MSC|BSC|HND|PGD|PHD)$", message = "Level must be one of PHD, MSC, BSC, HND, or PGD")
  private String level;
  @NotNull(message = "lecturerId can't be null")
  private Long lecturerId;
}
