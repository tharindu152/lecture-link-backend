package lk.ac.iit.lecture_link.api;

import lk.ac.iit.lecture_link.dto.ProgramDto;
import lk.ac.iit.lecture_link.service.custom.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/programs")
@RequiredArgsConstructor
@CrossOrigin
public class ProgramHttpController {

    private final ProgramService programService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ProgramDto createNewProgram(@RequestBody @Validated ProgramDto programDto) {
        return programService.saveProgram(programDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{program-id}", consumes = "application/json")
    public void updateProgramDetailsViaJson(@PathVariable("program-id") Long programId,
                                             @RequestBody @Validated ProgramDto programDto) {
        programDto.setId(programId);
        programService.updateProgramDetails(programDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{program-id}")
    public void deleteProgram(@PathVariable("program-id") Long programId) {
        programService.deleteProgram(programId);
    }

    @GetMapping(produces = "application/json")
    public List<ProgramDto> getAllPrograms() {
        return programService.getAllPrograms();
    }

    @GetMapping(value = "/{program-id}", produces = "application/json")
    public ProgramDto getProgram(@PathVariable("program-id") Long programId) {
        return programService.getProgram(programId);
    }

    @GetMapping(value = "/lecturer/{lecturer-id}", produces = "application/json")
    public Set<ProgramDto> getProgramsForLecturer(@PathVariable("lecturer-id") Long lecturerId) {
        return programService.getProgramsForLecturerId(lecturerId);
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public Page<ProgramDto> getFilteredPrograms(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "durationInDays", required = false) Integer durationInDays,
            @RequestParam(value = "studentCount", required = false) Integer studentCount,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return programService.getFilteredPrograms(name, description, level, durationInDays, studentCount, pageable);
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
