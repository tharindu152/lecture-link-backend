package lk.ac.iit.lecture_link.service.custom.impl;

import lk.ac.iit.lecture_link.dto.ProgramDto;
import lk.ac.iit.lecture_link.entity.Program;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.ProgramRepository;
import lk.ac.iit.lecture_link.service.custom.ProgramService;
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
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final Transformer transformer;

    private static final String PROGRAM_NOT_FOUND_MSG = "No program associated with the id";

    @Override
    public ProgramDto saveProgram(ProgramDto programDto) {
        Program program = transformer.fromProgramDto(programDto);

        program = programRepository.save(program);

        return transformer.toProgramDto(program);
    }

    @Override
    public void updateProgramDetails(ProgramDto programDto) {
        programRepository.findById(programDto.getId())
                .orElseThrow(() -> new AppException(404, PROGRAM_NOT_FOUND_MSG));

        Program newProgram = transformer.fromProgramDto(programDto);

        programRepository.save(newProgram);
    }

    @Override
    public void deleteProgram(Long programId) {
        Program program = programRepository.findById(programId).orElseThrow(() -> new AppException(404, PROGRAM_NOT_FOUND_MSG));

        programRepository.delete(program);
    }

    @Override
    public ProgramDto getProgram(Long programId) {
        Optional<Program> optProgram = programRepository.findById(programId);

        if (optProgram.isEmpty()) throw new AppException(404, PROGRAM_NOT_FOUND_MSG);

        return transformer.toProgramDto(optProgram.get());
    }

    @Override
    public List<ProgramDto> getAllPrograms() {

        List<Program> programList = programRepository.findAll();

        return programList.stream().map(transformer::toProgramDto).collect(Collectors.toList());

    }

    @Override
    public Page<ProgramDto> getFilteredPrograms(String name, String description, String level, Integer durationInDays,
                                                Integer studentCount, Pageable pageable) {

        Page<Program> programPage = programRepository.findFilteredPrograms(name, description, level, durationInDays,
                studentCount, pageable);

        return programPage.map(transformer::toProgramDto);
    }
}
