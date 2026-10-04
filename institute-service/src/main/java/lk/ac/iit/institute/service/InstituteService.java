package lk.ac.iit.institute.service;

import lk.ac.iit.institute.dto.InstituteDto;
import lk.ac.iit.institute.dto.InstituteRequest;
import lk.ac.iit.institute.entity.Institute;
import lk.ac.iit.institute.enums.InstituteStatus;
import lk.ac.iit.institute.exception.ResourceNotFoundException;
import lk.ac.iit.institute.repository.InstituteRepository;
import lk.ac.iit.institute.repository.ProgramRepository;
import lk.ac.iit.institute.repository.SubjectRepository;
import lk.ac.iit.institute.util.InstituteMapper;
import lk.ac.iit.institute.util.ImageStorageService;
import lk.ac.iit.institute.util.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class InstituteService {
    private final InstituteRepository repository;
    private final ProgramRepository programRepository;
    private final SubjectRepository subjectRepository;
    private final InstituteMapper mapper;
    private final ImageStorageService imageStorageService;

    public InstituteService(InstituteRepository repository, ProgramRepository programRepository,
                            SubjectRepository subjectRepository, InstituteMapper mapper,
                            ImageStorageService imageStorageService) {
        this.repository = repository;
        this.programRepository = programRepository;
        this.subjectRepository = subjectRepository;
        this.mapper = mapper;
        this.imageStorageService = imageStorageService;
    }

    public InstituteDto create(InstituteRequest request) {
        if (repository.findByEmailIgnoreCase(request.email()).isPresent()) {
            throw new IllegalArgumentException("An institute with this email already exists");
        }
        Institute institute = new Institute();
        apply(institute, request);
        return mapper.toDto(repository.save(institute));
    }

    public InstituteDto create(InstituteRequest request, MultipartFile logo) throws IOException {
        String logoPath = imageStorageService.store("institutes", logo);
        return create(withLogoUrl(request, logoPath));
    }

    public InstituteDto update(Long id, InstituteRequest request) {
        Institute institute = getEntity(id);
        repository.findByEmailIgnoreCase(request.email()).filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("An institute with this email already exists");
                });
        apply(institute, request);
        return mapper.toDto(repository.save(institute));
    }

    public InstituteDto update(Long id, InstituteRequest request, MultipartFile logo) throws IOException {
        Institute current = getEntity(id);
        String oldPath = current.getLogoUrl();
        String newPath = imageStorageService.store("institutes", logo);
        InstituteDto updated = update(id, newPath == null ? request : withLogoUrl(request, newPath));
        if (newPath != null && oldPath != null && !oldPath.equals(newPath)) {
            imageStorageService.delete(oldPath);
        }
        return updated;
    }

    public InstituteDto get(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<InstituteDto> getAll(String division) {
        List<Institute> institutes = division == null || division.isBlank()
                ? repository.findAll()
                : repository.findByDivisionContainingIgnoreCase(division);
        return institutes.stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<InstituteDto> getFiltered(String division, String status, Pageable pageable) {
        List<InstituteDto> filtered = repository.findAll().stream()
                .filter(institute -> division == null || division.isBlank()
                        || institute.getDivision().toLowerCase().contains(division.toLowerCase()))
                .filter(institute -> status == null || status.isBlank()
                        || institute.getStatus().name().equalsIgnoreCase(status))
                .map(mapper::toDto)
                .toList();
        return Pagination.page(filtered, pageable);
    }

    @Transactional(readOnly = true)
    public List<InstituteDto> getForLecturer(Long lecturerId) {
        var subjectIds = subjectRepository.findByLecturerId(lecturerId).stream()
                .map(subject -> subject.getId()).collect(java.util.stream.Collectors.toSet());
        if (subjectIds.isEmpty()) {
            return List.of();
        }
        var instituteIds = programRepository.findAll().stream()
                .filter(program -> program.getSubjectIds().stream().anyMatch(subjectIds::contains))
                .map(program -> program.getInstituteId())
                .distinct()
                .toList();
        return repository.findAllById(instituteIds).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public String getEmailBySubject(Long subjectId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject", subjectId);
        }
        return programRepository.findAll().stream()
                .filter(program -> program.getSubjectIds().contains(subjectId))
                .map(program -> repository.findById(program.getInstituteId()).orElse(null))
                .filter(java.util.Objects::nonNull)
                .map(Institute::getEmail)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Institute for subject", subjectId));
    }

    public void delete(Long id) {
        Institute institute = getEntity(id);
        repository.delete(institute);
        imageStorageService.delete(institute.getLogoUrl());
    }

    public InstituteDto updateRating(Long id, int rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        Institute institute = getEntity(id);
        int count = institute.getRatingsReceived() == null ? 0 : institute.getRatingsReceived();
        int current = institute.getCurrentRating() == null ? 0 : institute.getCurrentRating();
        institute.setCurrentRating((current * count + rating) / (count + 1));
        institute.setRatingsReceived(count + 1);
        return mapper.toDto(repository.save(institute));
    }

    public InstituteDto setSubscribed(Long id, boolean subscribed) {
        Institute institute = getEntity(id);
        institute.setSubscribed(subscribed);
        return mapper.toDto(repository.save(institute));
    }

    public InstituteDto deactivate(Long id) {
        Institute institute = getEntity(id);
        institute.setStatus(InstituteStatus.INACTIVE);
        return mapper.toDto(repository.save(institute));
    }

    private Institute getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Institute", id));
    }

    private void apply(Institute institute, InstituteRequest request) {
        institute.setName(request.name());
        institute.setEmail(request.email());
        institute.setDivision(request.division());
        institute.setMapsLocation(request.mapsLocation());
        institute.setTelephone(request.telephone());
        institute.setDescription(request.description());
        if (request.logoUrl() != null) {
            institute.setLogoUrl(request.logoUrl());
        }
        if (request.currentRating() != null) {
            institute.setCurrentRating(request.currentRating());
        }
        if (request.ratingsReceived() != null) {
            institute.setRatingsReceived(request.ratingsReceived());
        }
        if (request.subscribed() != null) {
            institute.setSubscribed(request.subscribed());
        }
        if (request.status() != null && !request.status().isBlank()) {
            institute.setStatus(InstituteStatus.valueOf(request.status().toUpperCase()));
        }
    }

    private InstituteRequest withLogoUrl(InstituteRequest request, String logoUrl) {
        return new InstituteRequest(request.name(), request.email(), request.division(),
                request.mapsLocation(), request.telephone(), request.description(), logoUrl,
                request.currentRating(), request.ratingsReceived(), request.subscribed(), request.status());
    }
}
