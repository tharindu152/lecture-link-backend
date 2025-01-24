package lk.ac.iit.lecture_link.service.custom;

import lk.ac.iit.lecture_link.dto.SubjectDto;
import lk.ac.iit.lecture_link.service.SuperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubjectService extends SuperService {

    SubjectDto saveSubject(SubjectDto subjectDto);

    void updateSubjectDetails(SubjectDto subjectDto);

    void deleteSubject(Long subjectId);

    SubjectDto getSubject(Long subjectId);

    List<SubjectDto> getAllSubjects();

    Page<SubjectDto> getFilteredSubjects(String name, String description, Integer noOfCredits, Boolean isAssigned,
                                         Pageable pageable);
}
