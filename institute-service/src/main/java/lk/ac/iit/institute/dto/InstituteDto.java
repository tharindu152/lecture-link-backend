package lk.ac.iit.institute.dto;

import lk.ac.iit.institute.enums.InstituteStatus;

import java.time.LocalDateTime;

public record InstituteDto(
        Long id,
        String name,
        String email,
        String division,
        String mapsLocation,
        String telephone,
        String description,
        Integer currentRating,
        Integer ratingsReceived,
        boolean subscribed,
        InstituteStatus status,
        String logoUrl,
        LocalDateTime createdOn,
        LocalDateTime updatedOn) {
}
