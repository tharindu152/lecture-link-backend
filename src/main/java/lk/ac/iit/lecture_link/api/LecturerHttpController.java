package lk.ac.iit.lecture_link.api;

import lk.ac.iit.lecture_link.dto.AiMatchResponseDto;
import lk.ac.iit.lecture_link.dto.LecturerDto;
import lk.ac.iit.lecture_link.dto.request.AiMatchRequestDto;
import lk.ac.iit.lecture_link.dto.request.LecturerReqDto;
import lk.ac.iit.lecture_link.service.custom.LecturerService;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lecturers")
@CrossOrigin
@RequiredArgsConstructor
public class LecturerHttpController {

    private final LecturerService lecturerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/register", consumes = "multipart/form-data", produces = "application/json")
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

    @GetMapping(value = "/institutes/{institute-id}", produces = "application/json")
    public Set<LecturerDto> getLecturersForInstitute(@PathVariable("institute-id") Long instituteId) {
        return lecturerService.getLecturersForInstituteId(instituteId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}/rating")
    public void updateLecturerRating(@PathVariable("lecturer-id") Long lecturerId,
                                     @RequestParam("newRating") int newRating) {
        lecturerService.updateLecturerRating(lecturerId, newRating);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}/deactivate")
    public void deactivateLecturer(@PathVariable("lecturer-id") Long lecturerId) {
        lecturerService.deactivateLecturer(lecturerId);
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public Page<LecturerDto> getFilteredLecturers(
            @RequestParam(value = "division", required = false) String division,
            @RequestParam(value = "payRateLower", required = false) BigDecimal payRateLower,
            @RequestParam(value = "payRateUpper", required = false) BigDecimal payRateUpper,
            @RequestParam(value = "qualification", required = false) String qualification,
            @RequestParam(value = "isAssigned", required = false) Boolean isAssigned,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "globalSearch", required = false) String globalSearch,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort) {

        Sort sortObj = Sort.by(parseSort(sort));

        Pageable pageable = PageRequest.of(page, size, sortObj);

        return lecturerService.getFilteredLecturers(
                division,
                payRateLower,
                payRateUpper,
                qualification,
                isAssigned,
                language,
                globalSearch,
                pageable
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}/subscribe")
    public void updateLecturerSubscription(@PathVariable("lecturer-id") Long lecturerId,
                                           @RequestParam("subscribed") boolean subscribed) {
        lecturerService.updateLecturerSubscription(lecturerId, subscribed);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}/assign")
    public void updateLecturerIsAssigned(@PathVariable("lecturer-id") Long lecturerId,
                                           @RequestParam("assign") boolean isAssigned) {
        lecturerService.updateLecturerIsAssigned(lecturerId, isAssigned);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/ai-match", consumes = "application/json", produces = "application/json")
    public AiMatchResponseDto getPrediction(@RequestBody AiMatchRequestDto requestDto) {
        return lecturerService.getPrediction(requestDto);
    }

}
