package lk.ac.iit.institute.service;

import lk.ac.iit.institute.dto.ProgramDto;
import lk.ac.iit.institute.entity.Institute;
import lk.ac.iit.institute.entity.Program;
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

import java.util.List;

@Service
@Transactional
public class ProgramService {
    private final ProgramRepository repository;
    private final InstituteRepository instituteRepository;
    private final SubjectRepository subjectRepository;
    private final InstituteMapper mapper;

    public ProgramService(ProgramRepository repository, InstituteRepository instituteRepository,
                          SubjectRepository subjectRepository, InstituteMapper mapper) {
        this.repository = repository;
        this.instituteRepository = instituteRepository;
        this.subjectRepository = subjectRepository;
        this.mapper = mapper;
    }

    public ProgramDto create(ProgramDto request) {
        Program program = new Program();
        apply(program, request);
        return mapper.toDto(repository.save(program));
    }

    public ProgramDto update(Long id, ProgramDto request) {
        Program program = getEntity(id);
        apply(program, request);
        return mapper.toDto(repository.save(program));
    }

    @Transactional(readOnly = true)
    public ProgramDto get(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<ProgramDto> getAll(Long instituteId) {
        List<Program> programs = instituteId == null
                ? repository.findAll() : repository.findByInstituteId(instituteId);
        return programs.stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ProgramDto> getForLecturer(Long lecturerId) {
        var subjectIds = subjectRepository.findByLecturerId(lecturerId).stream()
                .map(subject -> subject.getId()).collect(java.util.stream.Collectors.toSet());
        return repository.findAll().stream()
                .filter(program -> program.getSubjectIds().stream().anyMatch(subjectIds::contains))
                .map(mapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<ProgramDto> getFiltered(String name, String description, String level,
                                        Integer durationInDays, Integer studentCount, Pageable pageable) {
        var filtered = repository.findAll().stream()
                .filter(program -> name == null || name.isBlank()
                        || program.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(program -> description == null || description.isBlank()
                        || (program.getDescription() != null
                        && program.getDescription().toLowerCase().contains(description.toLowerCase())))
                .filter(program -> level == null || level.isBlank()
                        || program.getLevel().name().equalsIgnoreCase(level))
                .filter(program -> durationInDays == null
                        || java.util.Objects.equals(program.getDurationInDays(), durationInDays))
                .filter(program -> studentCount == null
                        || java.util.Objects.equals(program.getStudentCount(), studentCount))
                .map(mapper::toDto)
                .toList();
        return Pagination.page(filtered, pageable);
    }

    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    private Program getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Program", id));
    }

    private void apply(Program program, ProgramDto request) {
        if (request.name() == null || request.name().isBlank() || request.instituteId() == null
                || request.level() == null || request.language() == null || request.timePreference() == null) {
            throw new IllegalArgumentException(
                    "Program name, level, language, timePreference and instituteId are required");
        }
        Institute institute = instituteRepository.findById(request.instituteId())
                .orElseThrow(() -> new ResourceNotFoundException("Institute", request.instituteId()));
        if (request.subjectIds() != null && !subjectRepository.findAllById(request.subjectIds()).stream()
                .map(subject -> subject.getId()).collect(java.util.stream.Collectors.toSet())
                .containsAll(request.subjectIds())) {
            throw new IllegalArgumentException("One or more subjectIds do not exist");
        }
        program.setName(request.name());
        program.setDescription(request.description());
        program.setLevel(request.level());
        program.setDurationInDays(request.durationInDays());
        program.setStudentCount(request.studentCount());
        program.setLanguage(request.language());
        program.setBatchId(request.batchId());
        program.setHourlyPayRate(request.hourlyPayRate());
        program.setTimePreference(request.timePreference());
        program.setInstituteId(institute.getId());
        program.setSubjectIds(request.subjectIds() == null ? new java.util.LinkedHashSet<>()
                : new java.util.LinkedHashSet<>(request.subjectIds()));
    }
}
