package lk.ac.iit.institute.api;

import lk.ac.iit.institute.dto.ProgramDto;
import lk.ac.iit.institute.service.ProgramService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/programs")
@CrossOrigin
public class ProgramApi {
    private final ProgramService service;

    public ProgramApi(ProgramService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProgramDto create(@RequestBody ProgramDto request) {
        return service.create(request);
    }

    @GetMapping
    public List<ProgramDto> getAll(@RequestParam(required = false) Long instituteId) {
        return service.getAll(instituteId);
    }

    @GetMapping("/{id}")
    public ProgramDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/lecturer/{lecturerId}")
    public List<ProgramDto> getForLecturer(@PathVariable Long lecturerId) {
        return service.getForLecturer(lecturerId);
    }

    @PutMapping("/{id}")
    public ProgramDto update(@PathVariable Long id, @RequestBody ProgramDto request) {
        return service.update(id, request);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long id, @RequestBody ProgramDto request) {
        service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/filter")
    public Page<ProgramDto> filter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) Integer durationInDays,
            @RequestParam(required = false) Integer studentCount,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getFiltered(name, description, level, durationInDays, studentCount, pageable);
    }
}
