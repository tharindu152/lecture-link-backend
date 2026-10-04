package lk.ac.iit.institute.api;

import lk.ac.iit.institute.dto.SubjectDto;
import lk.ac.iit.institute.dto.FilteredSubjectDto;
import lk.ac.iit.institute.service.SubjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/subjects")
@CrossOrigin
public class SubjectApi {
    private final SubjectService service;

    public SubjectApi(SubjectService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDto create(@RequestBody SubjectDto request) {
        return service.create(request);
    }

    @GetMapping
    public List<SubjectDto> getAll(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) Long lecturerId) {
        return service.getAll(name, lecturerId);
    }

    @GetMapping("/{id}")
    public SubjectDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public SubjectDto update(@PathVariable Long id, @RequestBody SubjectDto request) {
        return service.update(id, request);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long id, @RequestBody SubjectDto request) {
        service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/filter")
    public Page<FilteredSubjectDto> filter(
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String programLevel,
            @RequestParam(required = false) Integer credits,
            @RequestParam(required = false) BigDecimal paymentLower,
            @RequestParam(required = false) BigDecimal paymentUpper,
            @RequestParam(required = false) Integer durationLower,
            @RequestParam(required = false) Integer durationUpper,
            @RequestParam(required = false) Integer studentLower,
            @RequestParam(required = false) Integer studentUpper,
            @RequestParam(required = false) String globalSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getFiltered(division, programLevel, credits, paymentLower, paymentUpper,
                durationLower, durationUpper, studentLower, studentUpper, globalSearch, pageable);
    }
}
