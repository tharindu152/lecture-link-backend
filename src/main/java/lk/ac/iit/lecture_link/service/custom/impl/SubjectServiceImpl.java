package lk.ac.iit.lecture_link.service.custom.impl;

import lk.ac.iit.lecture_link.dto.FilteredSubjectDto;
import lk.ac.iit.lecture_link.dto.SubjectDto;
import lk.ac.iit.lecture_link.entity.Subject;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.SubjectRepository;
import lk.ac.iit.lecture_link.service.custom.SubjectService;
import lk.ac.iit.lecture_link.service.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final Transformer transformer;

    private static final String SUBJECT_NOT_FOUND_MSG = "No subject associated with the id";

    @Override
    public SubjectDto saveSubject(SubjectDto subjectDto) {
        Subject subject = transformer.fromSubjectDto(subjectDto);

        subject = subjectRepository.save(subject);

        return transformer.toSubjectDto(subject);
    }

    @Override
    public void updateSubjectDetails(SubjectDto subjectDto) {
        subjectRepository.findById(subjectDto.getId())
                .orElseThrow(() -> new AppException(404, SUBJECT_NOT_FOUND_MSG));

        Subject newSubject = transformer.fromSubjectDto(subjectDto);

        subjectRepository.save(newSubject);
    }

    @Override
    public void deleteSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new AppException(404, SUBJECT_NOT_FOUND_MSG));

        subjectRepository.delete(subject);
    }

    @Override
    public SubjectDto getSubject(Long subjectId) {
        Optional<Subject> optSubject = subjectRepository.findById(subjectId);

        if (optSubject.isEmpty()) throw new AppException(404, SUBJECT_NOT_FOUND_MSG);

        return transformer.toSubjectDto(optSubject.get());
    }

    @Override
    public List<SubjectDto> getAllSubjects() {

        List<Subject> subjectList = subjectRepository.findAll();

        return subjectList.stream().map(transformer::toSubjectDto).collect(Collectors.toList());

    }

    @Override
    public Page<FilteredSubjectDto> getFilteredSubjects(
            String district, String programLevel, Integer credits,
            BigDecimal paymentLower, BigDecimal paymentUpper,
            Integer durationLower, Integer durationUpper,
            Integer studentLower, Integer studentUpper,
            String globalSearch, Pageable pageable) {

        Page<Object[]> resultPage = subjectRepository.findFilteredSubjects(
                district, programLevel, credits,
                paymentLower, paymentUpper,
                durationLower, durationUpper,
                studentLower, studentUpper,
                globalSearch, pageable
        );

        return resultPage.map(transformer::mapToSubjectDto);
    }
}
