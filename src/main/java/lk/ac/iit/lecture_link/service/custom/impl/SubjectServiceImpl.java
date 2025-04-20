package lk.ac.iit.lecture_link.service.custom.impl;

import lk.ac.iit.lecture_link.dto.FilteredSubjectDto;
import lk.ac.iit.lecture_link.dto.SubjectDto;
import lk.ac.iit.lecture_link.entity.Subject;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.SubjectRepository;
import lk.ac.iit.lecture_link.service.custom.SubjectService;
import lk.ac.iit.lecture_link.service.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(SubjectServiceImpl.class);

    private final SubjectRepository subjectRepository;
    private final Transformer transformer;

    private static final String SUBJECT_NOT_FOUND_MSG = "No subject associated with the id";

    @Override
    public SubjectDto saveSubject(SubjectDto subjectDto) {
        log.info("Saving subject with name: {}", subjectDto.getName());
        Subject subject = transformer.fromSubjectDto(subjectDto);

        subject = subjectRepository.save(subject);
        log.info("Subject saved successfully with ID: {}", subject.getId());

        return transformer.toSubjectDto(subject);
    }

    @Override
    public void updateSubjectDetails(SubjectDto subjectDto) {
        log.info("Updating subject details for ID: {}", subjectDto.getId());
        subjectRepository.findById(subjectDto.getId())
                .orElseThrow(() -> {
                    log.error(SUBJECT_NOT_FOUND_MSG + ": {}", subjectDto.getId());
                    return new AppException(404, SUBJECT_NOT_FOUND_MSG);
                });

        Subject newSubject = transformer.fromSubjectDto(subjectDto);
        subjectRepository.save(newSubject);
        log.info("Subject details updated successfully for ID: {}", subjectDto.getId());
    }

    @Override
    public void deleteSubject(Long subjectId) {
        log.info("Deleting subject with ID: {}", subjectId);
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> {
                    log.error(SUBJECT_NOT_FOUND_MSG + ": {}", subjectId);
                    return new AppException(404, SUBJECT_NOT_FOUND_MSG);
                });

        subjectRepository.delete(subject);
        log.info("Subject deleted successfully with ID: {}", subjectId);
    }

    @Override
    public SubjectDto getSubject(Long subjectId) {
        log.info("Fetching subject with ID: {}", subjectId);
        Optional<Subject> optSubject = subjectRepository.findById(subjectId);

        if (optSubject.isEmpty()) {
            log.error(SUBJECT_NOT_FOUND_MSG + ": {}", subjectId);
            throw new AppException(404, SUBJECT_NOT_FOUND_MSG);
        }

        log.info("Subject fetched successfully with ID: {}", subjectId);
        return transformer.toSubjectDto(optSubject.get());
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        log.info("Fetching all subjects");
        List<Subject> subjectList = subjectRepository.findAll();

        log.info("Fetched {} subjects", subjectList.size());
        return subjectList.stream().map(transformer::toSubjectDto).collect(Collectors.toList());
    }

    @Override
    public Page<FilteredSubjectDto> getFilteredSubjects(
            String division, String programLevel, Integer credits,
            BigDecimal paymentLower, BigDecimal paymentUpper,
            Integer durationLower, Integer durationUpper,
            Integer studentLower, Integer studentUpper,
            String globalSearch, Pageable pageable) {

        log.info("Fetching filtered subjects with criteria: division={}, programLevel={}, credits={}, paymentLower={}, paymentUpper={}, durationLower={}, durationUpper={}, studentLower={}, studentUpper={}, globalSearch={}",
                division, programLevel, credits, paymentLower, paymentUpper, durationLower, durationUpper, studentLower, studentUpper, globalSearch);

        Page<Object[]> resultPage = subjectRepository.findFilteredSubjects(
                division, programLevel, credits,
                paymentLower, paymentUpper,
                durationLower, durationUpper,
                studentLower, studentUpper,
                globalSearch, pageable
        );

        log.info("Fetched {} filtered subjects", resultPage.getTotalElements());
        return resultPage.map(transformer::mapToSubjectDto);
    }
}