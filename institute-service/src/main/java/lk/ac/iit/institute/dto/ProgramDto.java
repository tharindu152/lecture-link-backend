package lk.ac.iit.institute.dto;

import lk.ac.iit.institute.enums.Language;
import lk.ac.iit.institute.enums.ProgramLevel;
import lk.ac.iit.institute.enums.TimePreference;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record ProgramDto(
        Long id,
        String name,
        String description,
        ProgramLevel level,
        Integer durationInDays,
        Integer studentCount,
        Language language,
        String batchId,
        BigDecimal hourlyPayRate,
        TimePreference timePreference,
        Long instituteId,
        Set<Long> subjectIds,
        LocalDateTime createdOn,
        LocalDateTime updatedOn) {
}
