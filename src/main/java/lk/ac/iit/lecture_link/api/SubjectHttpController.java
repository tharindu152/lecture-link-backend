package lk.ac.iit.lecture_link.api;

import lk.ac.iit.lecture_link.dto.FilteredSubjectDto;
import lk.ac.iit.lecture_link.dto.SubjectDto;
import lk.ac.iit.lecture_link.service.custom.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
@CrossOrigin
public class SubjectHttpController {

    private final SubjectService subjectService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public SubjectDto createNewSubject(@RequestBody @Validated SubjectDto subjectDto) {
        return subjectService.saveSubject(subjectDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{subject-id}", consumes = "application/json")
    public void updateSubjectDetailsViaJson(@PathVariable("subject-id") Long subjectId,
                                            @RequestBody @Validated SubjectDto subjectDto) {
        subjectDto.setId(subjectId);
        subjectService.updateSubjectDetails(subjectDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{subject-id}")
    public void deleteSubject(@PathVariable("subject-id") Long subjectId) {
        subjectService.deleteSubject(subjectId);
    }

    @GetMapping(produces = "application/json")
    public List<SubjectDto> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping(value = "/{subject-id}", produces = "application/json")
    public SubjectDto getSubject(@PathVariable("subject-id") Long subjectId) {
        return subjectService.getSubject(subjectId);
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public Page<FilteredSubjectDto> getFilteredSubjects(
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "programLevel", required = false) String programLevel,
            @RequestParam(value = "credits", required = false) Integer credits,
            @RequestParam(value = "paymentLower", required = false) BigDecimal paymentLower,
            @RequestParam(value = "paymentUpper", required = false) BigDecimal paymentUpper,
            @RequestParam(value = "durationLower", required = false) Integer durationLower,
            @RequestParam(value = "durationUpper", required = false) Integer durationUpper,
            @RequestParam(value = "studentLower", required = false) Integer studentLower,
            @RequestParam(value = "studentUpper", required = false) Integer studentUpper,
            @RequestParam(value = "globalSearch", required = false) String globalSearch,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));

        return subjectService.getFilteredSubjects(
                district, programLevel, credits,
                paymentLower, paymentUpper,
                durationLower, durationUpper,
                studentLower, studentUpper,
                globalSearch, pageable
        );
    }

    private List<Sort.Order> parseSort(String sort) {
        return Arrays.stream(sort.split(","))
                .map(order -> {
                    String[] orderParts = order.split(":");
                    return new Sort.Order(
                            orderParts.length > 1 && orderParts[1].equalsIgnoreCase("desc")
                                    ? Sort.Direction.DESC : Sort.Direction.ASC,
                            orderParts[0]
                    );
                })
                .collect(Collectors.toList());
    }

}
