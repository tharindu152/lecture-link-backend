package lk.ac.iit.lecture_link.dto.request;

import lk.ac.iit.lecture_link.validation.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerReqDto implements Serializable {
    private long id;
    @NotBlank(message = "Lecturer name can't be empty")
    @Length(max = 255, message = "Lecturer name must not exceed 255 characters")
    private String name;
    @NotBlank(message = "District can't be empty")
    @Length(max = 500, message = "District must not exceed 500 characters")
    private String district;
    @NotBlank(message = "Maps location can't be empty")
    private String mapsLocation;
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Password can't be empty")
    @Length(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;
    @Pattern(regexp = "^(?:\\+94|0)?(?:7[01245678]\\d{7}|1\\d{8})$", message = "Invalid contact number")
    private String contactNo;
    @Min(0)
    @Max(5)
    private int currentRating;
    @Positive
    private int ratingsReceived;
    private Boolean subscribed;
    @DecimalMin(value = "0.0", inclusive = false, message = "Pay rate must be greater than 0")
    private BigDecimal hourlyPayRate;
    @Length(max = 255, message = "Preference must not exceed 255 characters")
    private String preference;
    @Image
    private MultipartFile picture;
    @NotBlank(message = "Status can't be empty")
    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be either 'ACTIVE' or 'INACTIVE'")
    private String status;
    @Positive
    private int lecturingExperience;
    @Length(max = 255, message = "Field of work must not exceed 255 characters")
    private String fieldOfWork;
    @Pattern(regexp = "^(WEEKEND|WEEKDAY|FLEXIBLE)$", message = "Time preference must be 'WEEKEND', 'WEEKDAY' or 'FLEXIBLE'")
    private String timePreference;
    private Boolean isAssigned;
    @Pattern(regexp = "^(ENGLISH|SINHALA|TAMIL)$", message = "Language must be 'ENGLISH', 'SINHALA' or 'TAMIL'")
    private String language;
}
