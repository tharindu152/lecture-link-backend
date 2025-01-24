package lk.ac.iit.lecture_link.api;

import lk.ac.iit.lecture_link.dto.QualificationDto;
import lk.ac.iit.lecture_link.service.custom.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/qualifications")
@CrossOrigin
public class QualificationHttpController {

    @Autowired
    private QualificationService qualificationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public QualificationDto createNewQualification(@RequestBody @Validated QualificationDto qualificationDto) {
        return qualificationService.saveQualification(qualificationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{qualification-id}", consumes = "application/json")
    public void updateQualificationDetailsViaJson(@PathVariable("qualification-id") Long qualificationId,
                                             @RequestBody @Validated QualificationDto qualificationDto) {
        qualificationDto.setId(qualificationId);
        qualificationService.updateQualificationDetails(qualificationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{qualification-id}")
    public void deleteQualification(@PathVariable("qualification-id") Long qualificationId) {
        qualificationService.deleteQualification(qualificationId);
    }

    @GetMapping(produces = "application/json")
    public List<QualificationDto> getAllQualifications() {
        return qualificationService.getAllQualifications();
    }

    @GetMapping(value = "/{qualification-id}", produces = "application/json")
    public QualificationDto getQualification(@PathVariable("qualification-id") Long qualificationId) {
        return qualificationService.getQualification(qualificationId);
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public Page<QualificationDto> getFilteredQualifications(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "awardingBody", required = false) String awardingBody,
            @RequestParam(value = "durationInDays", required = false) Integer durationInDays,
            @RequestParam(value = "discipline", required = false) String discipline,
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return qualificationService.getFilteredQualifications(name, awardingBody, durationInDays, discipline,
                level, pageable);
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
