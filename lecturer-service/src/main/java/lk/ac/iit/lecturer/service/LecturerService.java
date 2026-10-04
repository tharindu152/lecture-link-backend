package lk.ac.iit.lecturer.service;

import lk.ac.iit.lecturer.dto.LecturerDto;
import lk.ac.iit.lecturer.dto.LecturerRequest;
import lk.ac.iit.lecturer.entity.Lecturer;
import lk.ac.iit.lecturer.enums.LecturerStatus;
import lk.ac.iit.lecturer.exception.ResourceNotFoundException;
import lk.ac.iit.lecturer.repository.LecturerRepository;
import lk.ac.iit.lecturer.repository.QualificationRepository;
import lk.ac.iit.lecturer.util.ImageStorageService;
import lk.ac.iit.lecturer.util.LecturerMapper;
import lk.ac.iit.lecturer.util.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class LecturerService {
    private final LecturerRepository repository;
    private final QualificationRepository qualificationRepository;
    private final LecturerMapper mapper;
    private final ImageStorageService imageStorageService;

    public LecturerService(LecturerRepository repository, QualificationRepository qualificationRepository,
                           LecturerMapper mapper, ImageStorageService imageStorageService) {
        this.repository = repository;
        this.qualificationRepository = qualificationRepository;
        this.mapper = mapper;
        this.imageStorageService = imageStorageService;
    }

    public LecturerDto create(LecturerRequest request) {
        if (repository.findByEmailIgnoreCase(request.email()).isPresent()) {
            throw new IllegalArgumentException("A lecturer with this email already exists");
        }
        Lecturer lecturer = new Lecturer();
        apply(lecturer, request);
        return mapper.toDto(repository.save(lecturer));
    }

    public LecturerDto create(LecturerRequest request, MultipartFile picture) throws IOException {
        String picturePath = imageStorageService.store("lecturers", picture);
        return create(withPictureUrl(request, picturePath));
    }

    public LecturerDto update(Long id, LecturerRequest request) {
        Lecturer lecturer = getEntity(id);
        repository.findByEmailIgnoreCase(request.email()).filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("A lecturer with this email already exists");
                });
        apply(lecturer, request);
        return mapper.toDto(repository.save(lecturer));
    }

    public LecturerDto update(Long id, LecturerRequest request, MultipartFile picture) throws IOException {
        Lecturer current = getEntity(id);
        String oldPath = current.getPictureUrl();
        String newPath = imageStorageService.store("lecturers", picture);
        LecturerDto updated = update(id, newPath == null ? request : withPictureUrl(request, newPath));
        if (newPath != null && oldPath != null && !oldPath.equals(newPath)) {
            imageStorageService.delete(oldPath);
        }
        return updated;
    }

    @Transactional(readOnly = true)
    public LecturerDto get(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<LecturerDto> getAll(String division, Long instituteId) {
        List<Lecturer> lecturers = instituteId != null
                ? repository.findByInstituteId(instituteId)
                : division == null || division.isBlank()
                ? repository.findAll() : repository.findByDivisionContainingIgnoreCase(division);
        return lecturers.stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<LecturerDto> getFiltered(String division, java.math.BigDecimal payRateLower,
                                         java.math.BigDecimal payRateUpper, String qualification,
                                         Boolean assigned, String language, String globalSearch,
                                         Pageable pageable) {
        var lecturers = repository.findAll().stream()
                .filter(lecturer -> division == null || division.isBlank()
                        || lecturer.getDivision().toLowerCase().contains(division.toLowerCase()))
                .filter(lecturer -> payRateLower == null || (lecturer.getHourlyPayRate() != null
                        && lecturer.getHourlyPayRate().compareTo(payRateLower) >= 0))
                .filter(lecturer -> payRateUpper == null || (lecturer.getHourlyPayRate() != null
                        && lecturer.getHourlyPayRate().compareTo(payRateUpper) <= 0))
                .filter(lecturer -> assigned == null || assigned.equals(lecturer.getIsAssigned()))
                .filter(lecturer -> language == null || language.isBlank()
                        || (lecturer.getLanguage() != null
                        && lecturer.getLanguage().name().equalsIgnoreCase(language)))
                .filter(lecturer -> qualification == null || qualification.isBlank()
                        || qualificationRepository.findByLecturerId(lecturer.getId()).stream()
                        .anyMatch(item -> item.getName().toLowerCase().contains(qualification.toLowerCase())
                                || item.getLevel().name().equalsIgnoreCase(qualification)))
                .filter(lecturer -> globalSearch == null || globalSearch.isBlank()
                        || lecturer.getName().toLowerCase().contains(globalSearch.toLowerCase())
                        || lecturer.getEmail().toLowerCase().contains(globalSearch.toLowerCase())
                        || lecturer.getDivision().toLowerCase().contains(globalSearch.toLowerCase())
                        || (lecturer.getFieldOfWork() != null
                        && lecturer.getFieldOfWork().toLowerCase().contains(globalSearch.toLowerCase())))
                .map(mapper::toDto)
                .toList();
        return Pagination.page(lecturers, pageable);
    }

    public void delete(Long id) {
        Lecturer lecturer = getEntity(id);
        qualificationRepository.deleteAll(qualificationRepository.findByLecturerId(id));
        repository.deleteById(id);
        imageStorageService.delete(lecturer.getPictureUrl());
    }

    public LecturerDto updateRating(Long id, int rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        Lecturer lecturer = getEntity(id);
        int count = lecturer.getRatingsReceived() == null ? 0 : lecturer.getRatingsReceived();
        int current = lecturer.getCurrentRating() == null ? 0 : lecturer.getCurrentRating();
        lecturer.setCurrentRating((current * count + rating) / (count + 1));
        lecturer.setRatingsReceived(count + 1);
        return mapper.toDto(repository.save(lecturer));
    }

    public LecturerDto setSubscribed(Long id, boolean subscribed) {
        Lecturer lecturer = getEntity(id);
        lecturer.setSubscribed(subscribed);
        return mapper.toDto(repository.save(lecturer));
    }

    public LecturerDto setAssigned(Long id, boolean assigned) {
        Lecturer lecturer = getEntity(id);
        lecturer.setIsAssigned(assigned);
        return mapper.toDto(repository.save(lecturer));
    }

    public LecturerDto deactivate(Long id) {
        Lecturer lecturer = getEntity(id);
        lecturer.setStatus(LecturerStatus.INACTIVE);
        return mapper.toDto(repository.save(lecturer));
    }

    private Lecturer getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lecturer", id));
    }

    private void apply(Lecturer lecturer, LecturerRequest request) {
        lecturer.setName(request.name());
        lecturer.setDivision(request.division());
        lecturer.setMapsLocation(request.mapsLocation());
        lecturer.setEmail(request.email());
        lecturer.setContactNo(request.contactNo());
        lecturer.setHourlyPayRate(request.hourlyPayRate());
        lecturer.setPreference(request.preference());
        lecturer.setLanguage(mapper.language(request.language()));
        lecturer.setLecturingExperience(request.lecturingExperience());
        lecturer.setFieldOfWork(request.fieldOfWork());
        lecturer.setTimePreference(mapper.timePreference(request.timePreference()));
        lecturer.setInstituteId(request.instituteId());
        if (request.pictureUrl() != null) {
            lecturer.setPictureUrl(request.pictureUrl());
        }
        if (request.currentRating() != null) {
            lecturer.setCurrentRating(request.currentRating());
        }
        if (request.ratingsReceived() != null) {
            lecturer.setRatingsReceived(request.ratingsReceived());
        }
        if (request.isAssigned() != null) {
            lecturer.setIsAssigned(request.isAssigned());
        }
        if (request.subscribed() != null) {
            lecturer.setSubscribed(request.subscribed());
        }
        if (request.status() != null && !request.status().isBlank()) {
            lecturer.setStatus(mapper.lecturerStatus(request.status()));
        }
    }

    private LecturerRequest withPictureUrl(LecturerRequest request, String pictureUrl) {
        return new LecturerRequest(request.name(), request.division(), request.mapsLocation(), request.email(),
                request.contactNo(), request.hourlyPayRate(), request.preference(),
                request.language(), request.lecturingExperience(), request.fieldOfWork(), request.timePreference(),
                request.instituteId(), pictureUrl, request.currentRating(), request.ratingsReceived(),
                request.isAssigned(), request.subscribed(), request.status());
    }
}
