package lk.ac.iit.institute.util;

import lk.ac.iit.institute.dto.InstituteDto;
import lk.ac.iit.institute.dto.ProgramDto;
import lk.ac.iit.institute.dto.SubjectDto;
import lk.ac.iit.institute.entity.Institute;
import lk.ac.iit.institute.entity.Program;
import lk.ac.iit.institute.entity.Subject;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class InstituteMapper {
    private final ImageStorageService imageStorageService;

    public InstituteMapper(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    public InstituteDto toDto(Institute entity) {
        return new InstituteDto(entity.getId(), entity.getName(), entity.getEmail(), entity.getDivision(),
                entity.getMapsLocation(), entity.getTelephone(), entity.getDescription(), entity.getCurrentRating(),
                entity.getRatingsReceived(), entity.isSubscribed(), entity.getStatus(),
                imageStorageService.getUrl(entity.getLogoUrl()),
                entity.getCreatedOn(), entity.getUpdatedOn());
    }

    public ProgramDto toDto(Program entity) {
        return new ProgramDto(entity.getId(), entity.getName(), entity.getDescription(), entity.getLevel(),
                entity.getDurationInDays(), entity.getStudentCount(), entity.getLanguage(), entity.getBatchId(),
                entity.getHourlyPayRate(), entity.getTimePreference(), entity.getInstituteId(),
                Set.copyOf(entity.getSubjectIds()), entity.getCreatedOn(), entity.getUpdatedOn());
    }

    public SubjectDto toDto(Subject entity) {
        return new SubjectDto(entity.getId(), entity.getName(), entity.getNoOfCredits(), entity.getDescription(),
                entity.getIsAssigned(), entity.getLecturerId(), entity.getCreatedOn(), entity.getUpdatedOn());
    }
}
