package lk.ac.iit.lecture_link.api;

import lk.ac.iit.lecture_link.dto.LecturerDto;
import lk.ac.iit.lecture_link.dto.LecturerReqDto;
import lk.ac.iit.lecture_link.service.custom.LecturerService;
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
@RequestMapping("/api/v1/lecturers")
@CrossOrigin
public class LecturerHttpController {

    @Autowired
    private LecturerService lecturerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "multipart/form-data", produces = "application/json")
    public LecturerDto createNewLecturer(@ModelAttribute @Validated LecturerReqDto lecturerReqDto) {
        return lecturerService.saveLecturer(lecturerReqDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "multipart/form-data")
    public void updateLecturerDetailsViaMultipart(@PathVariable("lecturer-id") Long lecturerId,
                                                  @ModelAttribute @Validated LecturerReqDto lecturerReqDto) {
        lecturerReqDto.setId(lecturerId);
        lecturerService.updateLecturerDetails(lecturerReqDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "application/json")
    public void updateLecturerDetailsViaJson(@PathVariable("lecturer-id") Long lecturerId,
                                             @RequestBody @Validated LecturerDto lecturerDto) {
        lecturerDto.setId(lecturerId);
        lecturerService.updateLecturerDetails(lecturerDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{lecturer-id}")
    public void deleteLecturer(@PathVariable("lecturer-id") Long lecturerId) {
        lecturerService.deleteLecturer(lecturerId);
    }

    @GetMapping(produces = "application/json")
    public List<LecturerDto> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    @GetMapping(value = "/{lecturer-id}", produces = "application/json")
    public LecturerDto getLecturer(@PathVariable("lecturer-id") Long lecturerId) {
        return lecturerService.getLecturer(lecturerId);
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public Page<LecturerDto> getFilteredLecturers(
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "isAssigned", required = false) Boolean isAssigned,
            @RequestParam(value = "languages", required = false) String languages,
            @RequestParam(value = "subjectId", required = false) Long subjectId,
            @RequestParam(value = "qualificationIds", required = false) String qualification,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return lecturerService.getFilteredLecturers(district, status, isAssigned, languages, subjectId, qualification, pageable);
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
