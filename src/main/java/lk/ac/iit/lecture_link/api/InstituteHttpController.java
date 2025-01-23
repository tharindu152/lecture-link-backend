package lk.ac.iit.lecture_link.api;

import lk.ac.iit.lecture_link.dto.InstituteDto;
import lk.ac.iit.lecture_link.dto.InstituteReqDto;
import lk.ac.iit.lecture_link.service.custom.InstituteService;
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
@RequestMapping("/api/v1/institutes")
@CrossOrigin
public class InstituteHttpController {

    @Autowired
    private InstituteService instituteService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "multipart/form-data", produces = "application/json")
    public InstituteDto createNewInstitute(@ModelAttribute @Validated InstituteReqDto instituteReqDto) {
        return instituteService.saveInstitute(instituteReqDto);
    }

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
                                             @RequestBody @Validated InstituteReqDto instituteDto) {
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

}
