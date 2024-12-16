package lk.ac.iit.lecture_link.dto;

import lk.ac.iit.lecture_link.validation.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerDto implements Serializable {
    private long id;
    @NotBlank(message = "Lecturer name can't be empty")
    @Length(max = 255, message = "Lecturer name must not exceed 255 characters")
    private String name;
    @NotBlank(message = "Address can't be empty")
    @Length(max = 500, message = "Address must not exceed 500 characters")
    private String address;
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Password can't be empty")
    @Length(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;
    private String dateOfBirth;
    @Pattern(regexp = "^(?:\\+94|0)?(?:7[01245678]\\d{7}|1\\d{8})$", message = "Invalid contact number")
    private String contactNo;
    @Length(max = 1000, message = "Review must not exceed 1000 characters")
    private String review;
    @NotNull(message = "Pay rate can't be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Pay rate must be greater than 0")
    private BigDecimal payRate;
    @Length(max = 255, message = "Preference must not exceed 255 characters")
    private String preference;
    @Image
    private MultipartFile profilePic;
    @NotBlank(message = "Status can't be empty")
    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be either 'ACTIVE' or 'INACTIVE'")
    private String status;
    @NotNull(message = "isAssigned can not be null")
    private boolean isAssigned;
    @Length(max = 255, message = "Languages must not exceed 255 characters")
    private String languages;
}