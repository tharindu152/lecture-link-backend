package lk.ac.iit.institute.api;

import jakarta.validation.Valid;
import lk.ac.iit.institute.dto.InstituteDto;
import lk.ac.iit.institute.dto.InstituteMultipartRequest;
import lk.ac.iit.institute.dto.InstituteRequest;
import lk.ac.iit.institute.service.InstituteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/institutes")
@CrossOrigin
public class InstituteApi {
    private final InstituteService service;

    public InstituteApi(InstituteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstituteDto create(@Valid @RequestBody InstituteRequest request) {
        return service.create(request);
    }

    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public InstituteDto createMultipart(@Valid @ModelAttribute InstituteMultipartRequest request)
            throws java.io.IOException {
        return service.create(request.toRequest(), request.getLogo());
    }

    @GetMapping
    public List<InstituteDto> getAll(@RequestParam(required = false) String division) {
        return service.getAll(division);
    }

    @GetMapping("/{id}")
    public InstituteDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/lecturer/{lecturerId}")
    public List<InstituteDto> getForLecturer(@PathVariable Long lecturerId) {
        return service.getForLecturer(lecturerId);
    }

    @PutMapping("/{id}")
    public InstituteDto update(@PathVariable Long id, @Valid @RequestBody InstituteRequest request) {
        return service.update(id, request);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public InstituteDto updateMultipart(@PathVariable Long id,
                                        @Valid @ModelAttribute InstituteMultipartRequest request)
            throws java.io.IOException {
        return service.update(id, request.toRequest(), request.getLogo());
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long id, @Valid @RequestBody InstituteRequest request) {
        service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("/{id}/rating")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRating(@PathVariable Long id, @RequestParam int newRating) {
        service.updateRating(id, newRating);
    }

    @PatchMapping("/{id}/subscribe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSubscription(@PathVariable Long id, @RequestParam boolean subscribed) {
        service.setSubscribed(id, subscribed);
    }

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        service.deactivate(id);
    }

    @GetMapping("/filter")
    public Page<InstituteDto> filter(
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getFiltered(division, status, pageable);
    }

    @GetMapping("/email-by-subject/{subjectId}")
    public ResponseEntity<String> getEmailBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(service.getEmailBySubject(subjectId));
    }
}
