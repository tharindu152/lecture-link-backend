package lk.ac.iit.lecturer.dto;

import lk.ac.iit.lecturer.enums.QualificationLevel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record QualificationDto(
        Long id,
        String name,
        String awardingBody,
        Integer durationInDays,
        String discipline,
        LocalDate completedAt,
        QualificationLevel level,
        Long lecturerId,
        LocalDateTime createdOn,
        LocalDateTime updatedOn) {
}
