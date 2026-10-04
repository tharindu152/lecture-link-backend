package lk.ac.iit.lecturer.service;

import lk.ac.iit.lecturer.dto.QualificationDto;
import lk.ac.iit.lecturer.entity.Qualification;
import lk.ac.iit.lecturer.exception.ResourceNotFoundException;
import lk.ac.iit.lecturer.repository.LecturerRepository;
import lk.ac.iit.lecturer.repository.QualificationRepository;
import lk.ac.iit.lecturer.util.LecturerMapper;
import lk.ac.iit.lecturer.util.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QualificationService {
    private final QualificationRepository repository;
    private final LecturerRepository lecturerRepository;
    private final LecturerMapper mapper;

    public QualificationService(QualificationRepository repository, LecturerRepository lecturerRepository,
                                LecturerMapper mapper) {
        this.repository = repository;
        this.lecturerRepository = lecturerRepository;
        this.mapper = mapper;
    }

    public QualificationDto create(QualificationDto request) {
        Qualification qualification = new Qualification();
        apply(qualification, request);
        return mapper.toDto(repository.save(qualification));
    }

    public QualificationDto update(Long id, QualificationDto request) {
        Qualification qualification = getEntity(id);
        apply(qualification, request);
        return mapper.toDto(repository.save(qualification));
    }

    @Transactional(readOnly = true)
    public QualificationDto get(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<QualificationDto> getAll(Long lecturerId) {
        List<Qualification> qualifications = lecturerId == null
                ? repository.findAll() : repository.findByLecturerId(lecturerId);
        return qualifications.stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<QualificationDto> getFiltered(String name, String awardingBody, Integer durationInDays,
                                              String discipline, String level, Pageable pageable) {
        var filtered = repository.findAll().stream()
                .filter(item -> name == null || name.isBlank()
                        || item.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(item -> awardingBody == null || awardingBody.isBlank()
                        || item.getAwardingBody().toLowerCase().contains(awardingBody.toLowerCase()))
                .filter(item -> durationInDays == null
                        || java.util.Objects.equals(item.getDurationInDays(), durationInDays))
                .filter(item -> discipline == null || discipline.isBlank()
                        || (item.getDiscipline() != null
                        && item.getDiscipline().toLowerCase().contains(discipline.toLowerCase())))
                .filter(item -> level == null || level.isBlank()
                        || item.getLevel().name().equalsIgnoreCase(level))
                .map(mapper::toDto)
                .toList();
        return Pagination.page(filtered, pageable);
    }

    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    private Qualification getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Qualification", id));
    }

    private void apply(Qualification qualification, QualificationDto request) {
        if (request.name() == null || request.name().isBlank() || request.lecturerId() == null
                || request.awardingBody() == null || request.durationInDays() == null
                || request.completedAt() == null || request.level() == null) {
            throw new IllegalArgumentException(
                    "Qualification name, awardingBody, durationInDays, completedAt, level and lecturerId are required");
        }
        lecturerRepository.findById(request.lecturerId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer", request.lecturerId()));
        qualification.setName(request.name());
        qualification.setAwardingBody(request.awardingBody());
        qualification.setDurationInDays(request.durationInDays());
        qualification.setDiscipline(request.discipline());
        qualification.setCompletedAt(request.completedAt());
        qualification.setLevel(request.level());
        qualification.setLecturerId(request.lecturerId());
    }
}
