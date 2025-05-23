package lk.ac.iit.lecture_link.service.custom;

import lk.ac.iit.lecture_link.dto.InstituteDto;
import lk.ac.iit.lecture_link.dto.request.InstituteReqDto;
import lk.ac.iit.lecture_link.service.SuperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface InstituteService extends SuperService {

    InstituteDto saveInstitute(InstituteReqDto instituteReqDto);

    void updateInstituteDetails(InstituteReqDto instituteReqDto);

    void updateInstituteDetails(InstituteDto instituteTO);

    void deleteInstitute(Long instituteId);

    InstituteDto getInstitute(Long instituteId);

    List<InstituteDto> getAllInstitutes();

    Set<InstituteDto> getInstitutesForLecturerId(Long lecturerId);

    Page<InstituteDto> getFilteredInstitutes(String division, String status, Pageable pageable);

    InstituteDto getInstituteByEmailAndPassword(String email, String password);

    InstituteDto findInstituteByEmail(String email);

    void updateInstituteRating(Long instituteId, int newRating);

    void deactivateInstitute(Long instituteId);

    void updateInstituteSubscription(Long instituteId, boolean subscribed);

    String getInstituteEmailBySubjectId(Long subjectId);
}
