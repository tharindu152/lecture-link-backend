package lk.ac.iit.lecture_link.service.custom.impl;

import lk.ac.iit.lecture_link.dto.ProgramDto;
import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.entity.Program;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.LecturerRepository;
import lk.ac.iit.lecture_link.repository.ProgramRepository;
import lk.ac.iit.lecture_link.service.custom.ProgramService;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private static final Logger log = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final ProgramRepository programRepository;
    private final LecturerRepository lecturerRepository;
    private final Transformer transformer;

    private static final String PROGRAM_NOT_FOUND_MSG = "No program associated with the id";
    private static final String LECTURER_NOT_FOUND_MSG = "No lecturer associated with the id";

    @Override
    public ProgramDto saveProgram(ProgramDto programDto) {
        log.info("Saving program with name: {}", programDto.getName());
        Program program = transformer.fromProgramDto(programDto);

        program = programRepository.save(program);
        log.info("Program saved successfully with ID: {}", program.getId());

        return transformer.toProgramDto(program);
    }

    @Override
    public void updateProgramDetails(ProgramDto programDto) {
        log.info("Updating program details for ID: {}", programDto.getId());
        programRepository.findById(programDto.getId())
                .orElseThrow(() -> {
                    log.error(PROGRAM_NOT_FOUND_MSG + ": {}", programDto.getId());
                    return new AppException(404, PROGRAM_NOT_FOUND_MSG);
                });

        Program newProgram = transformer.fromProgramDto(programDto);
        programRepository.save(newProgram);
        log.info("Program details updated successfully for ID: {}", programDto.getId());
    }

    @Override
    public void deleteProgram(Long programId) {
        log.info("Deleting program with ID: {}", programId);
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> {
                    log.error(PROGRAM_NOT_FOUND_MSG + ": {}", programId);
                    return new AppException(404, PROGRAM_NOT_FOUND_MSG);
                });

        programRepository.delete(program);
        log.info("Program deleted successfully with ID: {}", programId);
    }

    @Override
    public ProgramDto getProgram(Long programId) {
        log.info("Fetching program with ID: {}", programId);
        Optional<Program> optProgram = programRepository.findById(programId);

        if (optProgram.isEmpty()) {
            log.error(PROGRAM_NOT_FOUND_MSG + ": {}", programId);
            throw new AppException(404, PROGRAM_NOT_FOUND_MSG);
        }

        log.info("Program fetched successfully with ID: {}", programId);
        return transformer.toProgramDto(optProgram.get());
    }

    @Override
    public List<ProgramDto> getAllPrograms() {
        log.info("Fetching all programs");
        List<Program> programList = programRepository.findAll();

        log.info("Fetched {} programs", programList.size());
        return programList.stream().map(transformer::toProgramDto).collect(Collectors.toList());
    }

    @Override
    public Set<ProgramDto> getProgramsForLecturerId(Long lecturerId) {
        log.info("Fetching programs for lecturer with ID: {}", lecturerId);
        Optional<Lecturer> optionalLecturer = lecturerRepository.findById(lecturerId);

        if (optionalLecturer.isEmpty()) {
            log.error(LECTURER_NOT_FOUND_MSG + ": {}", lecturerId);
            throw new AppException(404, LECTURER_NOT_FOUND_MSG);
        }

        Set<Program> programs = programRepository.findProgramsByLecturerId(lecturerId);
        log.info("Fetched {} programs for lecturer with ID: {}", programs.size(), lecturerId);
        return programs.stream().map(transformer::toProgramDto).collect(Collectors.toSet());
    }

    @Override
    public Page<ProgramDto> getFilteredPrograms(String name, String description, String level, Integer durationInDays,
                                                Integer studentCount, Pageable pageable) {
        log.info("Fetching filtered programs with criteria: name={}, description={}, level={}, durationInDays={}, studentCount={}",
                name, description, level, durationInDays, studentCount);

        Page<Program> programPage = programRepository.findFilteredPrograms(name, description, level, durationInDays,
                studentCount, pageable);

        log.info("Fetched {} programs matching the filter criteria", programPage.getTotalElements());
        return programPage.map(transformer::toProgramDto);
    }
}