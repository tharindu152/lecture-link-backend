package lk.ac.iit.lecture_link.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituteDto implements Serializable {
  private long id;
  @NotBlank(message = "Institute name can't be empty")
  @Length(max = 255, message = "Institute must be less than 255 characters")
  private String name;
  @NotBlank(message = "Password can't be empty")
  @Length(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
  private String password;
  @NotBlank(message = "Email can't be empty")
  @Email(message = "Invalid email address")
  private String email;
  @NotBlank(message = "District can't be empty")
  @Length(max = 500, message = "District must not exceed 500 characters")
  private String district;
  @NotBlank(message = "Maps location can't be empty")
  private String mapsLocation;
  @Pattern(regexp = "^(?:\\+94|0)?(?:7[01245678]\\d{7}|1\\d{8})$", message = "Invalid telephone number")
  private String telephone;
  @Length(max = 1000, message = "Description must not exceed 1000 characters")
  private String description;
  @Min(0)
  @Max(5)
  private int currentRating;
  @Positive
  private int ratingsReceived;
  private Boolean subscribed;
  private String logo;
  @NotBlank(message = "Status can't be empty")
  @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be either 'ACTIVE' or 'INACTIVE'")
  private String status;
  private Set<ProgramDto> programs;
}