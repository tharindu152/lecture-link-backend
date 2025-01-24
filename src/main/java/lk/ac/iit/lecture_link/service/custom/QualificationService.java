package lk.ac.iit.lecture_link.service.custom;

import lk.ac.iit.lecture_link.dto.QualificationDto;
import lk.ac.iit.lecture_link.service.SuperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QualificationService extends SuperService {

    QualificationDto saveQualification(QualificationDto qualificationDto);

    void updateQualificationDetails(QualificationDto qualificationDto);

    void deleteQualification(Long qualificationId);

    QualificationDto getQualification(Long qualificationId);

    List<QualificationDto> getAllQualifications();

    Page<QualificationDto> getFilteredQualifications(String name, String awardingBody, Integer durationInDays,
                                                     String discipline, String level, Pageable pageable);
}
