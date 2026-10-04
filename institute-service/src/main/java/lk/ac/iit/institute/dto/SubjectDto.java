package lk.ac.iit.institute.dto;

import java.time.LocalDateTime;

public record SubjectDto(
        Long id,
        String name,
        Integer noOfCredits,
        String description,
        Boolean isAssigned,
        Long lecturerId,
        LocalDateTime createdOn,
        LocalDateTime updatedOn) {
}
