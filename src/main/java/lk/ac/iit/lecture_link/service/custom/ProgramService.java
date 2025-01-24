package lk.ac.iit.lecture_link.service.custom;

import lk.ac.iit.lecture_link.dto.ProgramDto;
import lk.ac.iit.lecture_link.service.SuperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProgramService extends SuperService {

    ProgramDto saveProgram(ProgramDto programDto);

    void updateProgramDetails(ProgramDto programDto);

    void deleteProgram(Long programId);

    ProgramDto getProgram(Long programId);

    List<ProgramDto> getAllPrograms();

    Page<ProgramDto> getFilteredPrograms(String name, String description, String level, Integer durationInDays,
                                         Integer studentCount, Pageable pageable);
}
