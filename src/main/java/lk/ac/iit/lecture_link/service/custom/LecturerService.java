package lk.ac.iit.lecture_link.service.custom;

import lk.ac.iit.lecture_link.dto.AiMatchResponseDto;
import lk.ac.iit.lecture_link.dto.LecturerDto;
import lk.ac.iit.lecture_link.dto.request.AiMatchRequestDto;
import lk.ac.iit.lecture_link.dto.request.LecturerReqDto;
import lk.ac.iit.lecture_link.service.SuperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface LecturerService extends SuperService {

    LecturerDto saveLecturer(LecturerReqDto lecturerReqDto);

    void updateLecturerDetails(LecturerReqDto lecturerReqDto);

    void updateLecturerDetails(LecturerDto lecturerTO);

    void deleteLecturer(Long lecturerId);

    LecturerDto getLecturer(Long lecturerId);

    List<LecturerDto> getAllLecturers();

    Set<LecturerDto> getLecturersForInstituteId(Long instituteId);

    Page<LecturerDto> getFilteredLecturers(
            String division,
            BigDecimal payRateLower,
            BigDecimal payRateUpper,
            String qualification,
            Boolean isAssigned,
            String language,
            String globalSearch,
            Pageable pageable);

    LecturerDto getLecturerByEmailAndPassword(String email, String password);

    LecturerDto findLecturerByEmail(String email);

    void updateLecturerRating(Long lecturerId, int newRating);

    void deactivateLecturer(Long lecturerId);

    void updateLecturerSubscription(Long lecturerId, boolean subscribed);

    AiMatchResponseDto getPrediction(AiMatchRequestDto requestDto);

}
