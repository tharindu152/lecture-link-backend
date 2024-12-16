package lk.ac.iit.lecture_link.api;

import lk.ac.iit.lecture_link.converter.Level;
import lk.ac.iit.lecture_link.converter.Status;
import lk.ac.iit.lecture_link.service.custom.LecturerService;
import lk.ac.iit.lecture_link.dto.LecturerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lecturers")
@CrossOrigin
public class LecturerHttpController {

    @Autowired
    private LecturerService lecturerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "multipart/form-data", produces = "application/json")
    public LecturerDto createNewLecturer(@ModelAttribute @Validated LecturerDto lecturerDto) {
        return lecturerService.saveLecturer(lecturerDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "multipart/form-data")
    public void updateLecturerDetailsViaMultipart(@PathVariable("lecturer-id") Long lecturerId,
                                                  @ModelAttribute @Validated LecturerDto lecturerDto) {
        lecturerDto.setId(lecturerId);
        lecturerService.updateLecturerDetails(lecturerDto);
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
        return lecturerService.getLecturers(null);
    }

    @GetMapping(value = "/{lecturer-id}", produces = "application/json")
    public LecturerDto getLecturerDetails(@PathVariable("lecturer-id") Long lecturerId) {
        return lecturerService.getLecturerDetails(lecturerId);
    }

    @GetMapping(params = "type=active", produces = "application/json")
    public List<LecturerDto> getBscLevelLecturers() {
        return lecturerService.getLecturers(Status.ACTIVE);
    }

}
