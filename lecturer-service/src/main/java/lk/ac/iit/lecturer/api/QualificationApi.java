package lk.ac.iit.lecturer.api;

import lk.ac.iit.lecturer.dto.QualificationDto;
import lk.ac.iit.lecturer.service.QualificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/qualifications")
@CrossOrigin
public class QualificationApi {
    private final QualificationService service;

    public QualificationApi(QualificationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QualificationDto create(@RequestBody QualificationDto request) {
        return service.create(request);
    }

    @GetMapping
    public List<QualificationDto> getAll(@RequestParam(required = false) Long lecturerId) {
        return service.getAll(lecturerId);
    }

    @GetMapping("/{id}")
    public QualificationDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/filter")
    public Page<QualificationDto> filter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String awardingBody,
            @RequestParam(required = false) Integer durationInDays,
            @RequestParam(required = false) String discipline,
            @RequestParam(required = false) String level,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getFiltered(name, awardingBody, durationInDays, discipline, level, pageable);
    }

    @PutMapping("/{id}")
    public QualificationDto update(@PathVariable Long id, @RequestBody QualificationDto request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
