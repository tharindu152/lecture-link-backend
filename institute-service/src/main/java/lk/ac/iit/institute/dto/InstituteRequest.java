package lk.ac.iit.institute.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InstituteRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String division,
        String mapsLocation,
        String telephone,
        String description,
        String logoUrl,
        Integer currentRating,
        Integer ratingsReceived,
        Boolean subscribed,
        String status) {
}
