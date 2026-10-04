package lk.ac.iit.lecturer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDto(
        @NotBlank @Email String toEmail,
        @NotBlank String subject,
        @NotBlank String body) {
}
