package lk.ac.iit.lecture_link.service.custom;

import lk.ac.iit.lecture_link.converter.Status;
import lk.ac.iit.lecture_link.service.SuperService;
import lk.ac.iit.lecture_link.dto.LecturerDto;

import java.util.List;

public interface LecturerService extends SuperService {

    LecturerDto saveLecturer(LecturerDto lecturerDto);

    void updateLecturerDetails(LecturerDto lecturerDto);

    void deleteLecturer(Long lecturerId);

    LecturerDto getLecturerDetails(Long lecturerId);

    List<LecturerDto> getLecturers(Status status);
}
