package lk.ac.iit.institute.service;

import lk.ac.iit.institute.dto.SubjectDto;
import lk.ac.iit.institute.dto.FilteredSubjectDto;
import lk.ac.iit.institute.entity.Program;
import lk.ac.iit.institute.entity.Subject;
import lk.ac.iit.institute.exception.ResourceNotFoundException;
import lk.ac.iit.institute.repository.InstituteRepository;
import lk.ac.iit.institute.repository.ProgramRepository;
import lk.ac.iit.institute.repository.SubjectRepository;
import lk.ac.iit.institute.util.InstituteMapper;
import lk.ac.iit.institute.util.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class SubjectService {
    private final SubjectRepository repository;
    private final ProgramRepository programRepository;
    private final InstituteRepository instituteRepository;
    private final InstituteMapper mapper;

    public SubjectService(SubjectRepository repository, ProgramRepository programRepository,
                          InstituteRepository instituteRepository, InstituteMapper mapper) {
        this.repository = repository;
        this.programRepository = programRepository;
        this.instituteRepository = instituteRepository;
        this.mapper = mapper;
    }

    public SubjectDto create(SubjectDto request) {
        Subject subject = new Subject();
        apply(subject, request);
        return mapper.toDto(repository.save(subject));
    }

    public SubjectDto update(Long id, SubjectDto request) {
        Subject subject = getEntity(id);
        apply(subject, request);
        return mapper.toDto(repository.save(subject));
    }

    @Transactional(readOnly = true)
    public SubjectDto get(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<SubjectDto> getAll(String name, Long lecturerId) {
        if (lecturerId != null) {
            return repository.findByLecturerId(lecturerId).stream().map(mapper::toDto).toList();
        }
        List<Subject> subjects = name == null || name.isBlank()
                ? repository.findAll() : repository.findByNameContainingIgnoreCase(name);
        return subjects.stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<FilteredSubjectDto> getFiltered(
            String division, String programLevel, Integer credits,
            BigDecimal paymentLower, BigDecimal paymentUpper,
            Integer durationLower, Integer durationUpper,
            Integer studentLower, Integer studentUpper,
            String globalSearch, Pageable pageable) {
        var programs = programRepository.findAll();
        var results = repository.findAll().stream()
                .flatMap(subject -> programs.stream()
                        .filter(program -> program.getSubjectIds().contains(subject.getId()))
                        .map(program -> new SubjectProgram(subject, program)))
                .filter(pair -> division == null || division.isBlank()
                        || instituteRepository.findById(pair.program().getInstituteId())
                        .map(institute -> institute.getDivision().toLowerCase().contains(division.toLowerCase()))
                        .orElse(false))
                .filter(pair -> programLevel == null || programLevel.isBlank()
                        || pair.program().getLevel().name().equalsIgnoreCase(programLevel))
                .filter(pair -> credits == null || pair.subject().getNoOfCredits().equals(credits))
                .filter(pair -> paymentLower == null || (pair.program().getHourlyPayRate() != null
                        && pair.program().getHourlyPayRate().compareTo(paymentLower) >= 0))
                .filter(pair -> paymentUpper == null || (pair.program().getHourlyPayRate() != null
                        && pair.program().getHourlyPayRate().compareTo(paymentUpper) <= 0))
                .filter(pair -> durationLower == null || (pair.program().getDurationInDays() != null
                        && pair.program().getDurationInDays() >= durationLower))
                .filter(pair -> durationUpper == null || (pair.program().getDurationInDays() != null
                        && pair.program().getDurationInDays() <= durationUpper))
                .filter(pair -> studentLower == null || (pair.program().getStudentCount() != null
                        && pair.program().getStudentCount() >= studentLower))
                .filter(pair -> studentUpper == null || (pair.program().getStudentCount() != null
                        && pair.program().getStudentCount() <= studentUpper))
                .filter(pair -> globalSearch == null || globalSearch.isBlank()
                        || pair.subject().getName().toLowerCase().contains(globalSearch.toLowerCase())
                        || pair.program().getName().toLowerCase().contains(globalSearch.toLowerCase()))
                .map(pair -> new FilteredSubjectDto(pair.subject().getId(), pair.subject().getName(),
                        pair.program().getLevel().name(), pair.subject().getNoOfCredits(),
                        pair.program().getStudentCount(), pair.program().getDurationInDays(),
                        instituteRepository.findById(pair.program().getInstituteId())
                                .map(institute -> institute.getDivision()).orElse(null),
                        pair.program().getHourlyPayRate()))
                .toList();
        return Pagination.page(results, pageable);
    }

    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    private Subject getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject", id));
    }

    private void apply(Subject subject, SubjectDto request) {
        if (request.name() == null || request.name().isBlank() || request.noOfCredits() == null
                || request.noOfCredits() < 0 || request.noOfCredits() > 4) {
            throw new IllegalArgumentException("Subject name and credits between 0 and 4 are required");
        }
        subject.setName(request.name());
        subject.setNoOfCredits(request.noOfCredits());
        subject.setDescription(request.description());
        subject.setIsAssigned(request.isAssigned() == null ? false : request.isAssigned());
        subject.setLecturerId(request.lecturerId());
    }

    private record SubjectProgram(Subject subject, Program program) {
    }
}
