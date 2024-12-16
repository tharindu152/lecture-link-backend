package lk.ac.iit.lecture_link.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lk.ac.iit.lecture_link.validation.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

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
  @Pattern(regexp = "^(?:\\+94|0)?(?:7[01245678]\\d{7}|1\\d{8})$", message = "Invalid telephone number")
  private String telephone;
  @Length(max = 100, message = "UGC Registration Number must not exceed 100 characters")
  private String ugcRegNo;
  @Length(max = 1000, message = "Description must not exceed 1000 characters")
  private String description;
  @Length(max = 500, message = "Review must not exceed 500 characters")
  private String review;
  @NotNull(message = "Subscription status is required")
  private boolean subscribed;
  @Image
  private MultipartFile logo;
  @NotBlank(message = "Status can't be empty")
  @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be either 'ACTIVE' or 'INACTIVE'")
  private String status;
}