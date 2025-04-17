package lk.ac.iit.lecture_link.api;

import lk.ac.iit.lecture_link.dto.InstituteDto;
import lk.ac.iit.lecture_link.dto.request.InstituteReqDto;
import lk.ac.iit.lecture_link.service.custom.InstituteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/institutes")
@RequiredArgsConstructor
@CrossOrigin
public class InstituteHttpController {

    private final InstituteService instituteService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{institute-id}", consumes = "multipart/form-data")
    public void updateInstituteDetailsViaMultipart(@PathVariable("institute-id") Long instituteId,
                                                   @ModelAttribute @Validated InstituteReqDto instituteReqDto) {
        instituteReqDto.setId(instituteId);
        instituteService.updateInstituteDetails(instituteReqDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{institute-id}", consumes = "application/json")
    public void updateInstituteDetailsViaJson(@PathVariable("institute-id") Long instituteId,
                                              @RequestBody @Validated InstituteDto instituteDto) {
        instituteDto.setId(instituteId);
        instituteService.updateInstituteDetails(instituteDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{institute-id}")
    public void deleteInstitute(@PathVariable("institute-id") Long instituteId) {
        instituteService.deleteInstitute(instituteId);
    }

    @GetMapping(produces = "application/json")
    public List<InstituteDto> getAllInstitutes() {
        return instituteService.getAllInstitutes();
    }

    @GetMapping(value = "/{institute-id}", produces = "application/json")
    public InstituteDto getInstitute(@PathVariable("institute-id") Long instituteId) {
        return instituteService.getInstitute(instituteId);
    }

    @GetMapping(value = "/lecturer/{lecturer-id}", produces = "application/json")
    public Set<InstituteDto> getInstitutesForLecturer(@PathVariable("lecturer-id") Long lecturerId) {
        return instituteService.getInstitutesForLecturerId(lecturerId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{institute-id}/rating")
    public void updateInstituteRating(@PathVariable("institute-id") Long instituteId,
                                      @RequestParam("newRating") int newRating) {
        instituteService.updateInstituteRating(instituteId, newRating);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{institute-id}/deactivate")
    public void deactivateInstitute(@PathVariable("institute-id") Long instituteId) {
        instituteService.deactivateInstitute(instituteId);
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public Page<InstituteDto> getFilteredInstitutes(
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return instituteService.getFilteredInstitutes(district, status, pageable);
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
    @PatchMapping(value = "/{institute-id}/subscribe")
    public void updateInstituteSubscription(@PathVariable("institute-id") Long instituteId,
                                            @RequestParam("subscribed") boolean subscribed) {
        instituteService.updateInstituteSubscription(instituteId, subscribed);
    }

    @GetMapping("/email-by-subject/{subjectId}")
    public ResponseEntity<String> getInstituteEmailBySubjectId(@PathVariable Long subjectId) {
        String email = instituteService.getInstituteEmailBySubjectId(subjectId);
        if (email != null) {
            return ResponseEntity.ok(email);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
