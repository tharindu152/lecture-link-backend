package lk.ac.iit.lecture_link.service.custom.impl;

import lk.ac.iit.lecture_link.dto.QualificationDto;
import lk.ac.iit.lecture_link.entity.Qualification;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.QualificationRepository;
import lk.ac.iit.lecture_link.service.custom.QualificationService;
import lk.ac.iit.lecture_link.service.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(QualificationServiceImpl.class);

    private final QualificationRepository qualificationRepository;
    private final Transformer transformer;

    private static final String QUALIFICATION_NOT_FOUND_MSG = "No qualification associated with the id";

    @Override
    public QualificationDto saveQualification(QualificationDto qualificationDto) {
        log.info("Saving qualification with name: {}", qualificationDto.getName());
        Qualification qualification = transformer.fromQualificationDto(qualificationDto);

        qualification = qualificationRepository.save(qualification);
        log.info("Qualification saved successfully with ID: {}", qualification.getId());

        return transformer.toQualificationDto(qualification);
    }

    @Override
    public void updateQualificationDetails(QualificationDto qualificationDto) {
        log.info("Updating qualification details for ID: {}", qualificationDto.getId());
        qualificationRepository.findById(qualificationDto.getId())
                .orElseThrow(() -> {
                    log.error(QUALIFICATION_NOT_FOUND_MSG + ": {}", qualificationDto.getId());
                    return new AppException(404, QUALIFICATION_NOT_FOUND_MSG);
                });

        Qualification newQualification = transformer.fromQualificationDto(qualificationDto);
        qualificationRepository.save(newQualification);
        log.info("Qualification details updated successfully for ID: {}", qualificationDto.getId());
    }

    @Override
    public void deleteQualification(Long qualificationId) {
        log.info("Deleting qualification with ID: {}", qualificationId);
        Qualification qualification = qualificationRepository.findById(qualificationId)
                .orElseThrow(() -> {
                    log.error(QUALIFICATION_NOT_FOUND_MSG + ": {}", qualificationId);
                    return new AppException(404, QUALIFICATION_NOT_FOUND_MSG);
                });

        qualificationRepository.delete(qualification);
        log.info("Qualification deleted successfully with ID: {}", qualificationId);
    }

    @Override
    public QualificationDto getQualification(Long qualificationId) {
        log.info("Fetching qualification with ID: {}", qualificationId);
        Optional<Qualification> optQualification = qualificationRepository.findById(qualificationId);

        if (optQualification.isEmpty()) {
            log.error(QUALIFICATION_NOT_FOUND_MSG + ": {}", qualificationId);
            throw new AppException(404, QUALIFICATION_NOT_FOUND_MSG);
        }

        log.info("Qualification fetched successfully with ID: {}", qualificationId);
        return transformer.toQualificationDto(optQualification.get());
    }

    @Override
    public List<QualificationDto> getAllQualifications() {
        log.info("Fetching all qualifications");
        List<Qualification> qualificationList = qualificationRepository.findAll();

        log.info("Fetched {} qualifications", qualificationList.size());
        return qualificationList.stream().map(transformer::toQualificationDto).collect(Collectors.toList());
    }

    @Override
    public Page<QualificationDto> getFilteredQualifications(String name, String awardingBody, Integer durationInDays,
                                                            String discipline, String level, Pageable pageable) {
        log.info("Fetching filtered qualifications with criteria: name={}, awardingBody={}, durationInDays={}, discipline={}, level={}",
                name, awardingBody, durationInDays, discipline, level);

        Page<Qualification> qualificationPage = qualificationRepository.findFilteredQualifications(name, awardingBody,
                durationInDays, discipline, level, pageable);

        log.info("Fetched {} qualifications matching the filter criteria", qualificationPage.getTotalElements());
        return qualificationPage.map(transformer::toQualificationDto);
    }
}