package lk.ac.iit.lecture_link.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lk.ac.iit.lecture_link.entity.Program;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto implements Serializable {
  private long id;
  @NotBlank(message = "Subject name can't be empty")
  @Length(max = 255, message = "Subject name must not exceed 255 characters")
  private String name;
  @Positive(message = "Number of credits must be positive")
  private int noOfCredits;
  @Length(max = 1000, message = "Description must not exceed 1000 characters")
  private String description;
  @NotNull(message = "isAssigned can not be null")
  private Boolean isAssigned;
  private long lecturerId;
  @JsonIgnore
  private Set<Program> programs;
}
