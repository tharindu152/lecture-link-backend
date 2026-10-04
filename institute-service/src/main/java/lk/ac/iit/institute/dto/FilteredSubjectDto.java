package lk.ac.iit.institute.dto;

import java.math.BigDecimal;

public record FilteredSubjectDto(
        Long id,
        String name,
        String level,
        Integer noOfCredits,
        Integer studentCount,
        Integer durationInDays,
        String division,
        BigDecimal payment) {
}
