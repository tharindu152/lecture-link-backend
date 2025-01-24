package lk.ac.iit.lecture_link.service.custom.impl;

import lk.ac.iit.lecture_link.dto.QualificationDto;
import lk.ac.iit.lecture_link.entity.Qualification;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.QualificationRepository;
import lk.ac.iit.lecture_link.service.custom.QualificationService;
import lk.ac.iit.lecture_link.service.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QualificationServiceImpl implements QualificationService {

    private final QualificationRepository qualificationRepository;
    private final Transformer transformer;

    private static final String QUALIFICATION_NOT_FOUND_MSG = "No qualification associated with the id";

    @Override
    public QualificationDto saveQualification(QualificationDto qualificationDto) {
        Qualification qualification = transformer.fromQualificationDto(qualificationDto);

        qualification = qualificationRepository.save(qualification);

        return transformer.toQualificationDto(qualification);
    }

    @Override
    public void updateQualificationDetails(QualificationDto qualificationDto) {
        qualificationRepository.findById(qualificationDto.getId())
                .orElseThrow(() -> new AppException(404, QUALIFICATION_NOT_FOUND_MSG));

        Qualification newQualification = transformer.fromQualificationDto(qualificationDto);

        qualificationRepository.save(newQualification);
    }

    @Override
    public void deleteQualification(Long qualificationId) {
        Qualification qualification = qualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new AppException(404, QUALIFICATION_NOT_FOUND_MSG));

        qualificationRepository.delete(qualification);
    }

    @Override
    public QualificationDto getQualification(Long qualificationId) {
        Optional<Qualification> optQualification = qualificationRepository.findById(qualificationId);

        if (optQualification.isEmpty()) throw new AppException(404, QUALIFICATION_NOT_FOUND_MSG);

        return transformer.toQualificationDto(optQualification.get());
    }

    @Override
    public List<QualificationDto> getAllQualifications() {

        List<Qualification> qualificationList = qualificationRepository.findAll();

        return qualificationList.stream().map(transformer::toQualificationDto).collect(Collectors.toList());

    }

    @Override
    public Page<QualificationDto> getFilteredQualifications(String name, String awardingBody, Integer durationInDays,
                                                            String discipline, String level, Pageable pageable) {

        Page<Qualification> qualificationPage = qualificationRepository.findFilteredQualifications(name, awardingBody,
                durationInDays, discipline, level, pageable);

        return qualificationPage.map(transformer::toQualificationDto);
    }
}
