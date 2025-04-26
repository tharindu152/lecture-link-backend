package lk.ac.iit.lecture_link.service.custom.impl;

import lk.ac.iit.lecture_link.dto.ProgramDto;
import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.entity.Program;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.LecturerRepository;
import lk.ac.iit.lecture_link.repository.ProgramRepository;
import lk.ac.iit.lecture_link.service.util.Transformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgramServiceImplTest {

    @InjectMocks
    private ProgramServiceImpl programService;

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private LecturerRepository lecturerRepository;

    @Mock
    private Transformer transformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveProgram_Success() {
        // Arrange
        ProgramDto programDto = mock(ProgramDto.class);
        Program program = mock(Program.class);
        when(transformer.fromProgramDto(programDto)).thenReturn(program);
        when(programRepository.save(program)).thenReturn(program);
        when(transformer.toProgramDto(program)).thenReturn(programDto);

        // Act
        ProgramDto result = programService.saveProgram(programDto);

        // Assert
        assertNotNull(result);
        verify(programRepository, times(1)).save(program);
    }

    @Test
    void updateProgramDetails_Success() {
        // Arrange
        ProgramDto programDto = mock(ProgramDto.class);
        Program program = mock(Program.class);
        when(programDto.getId()).thenReturn(1L);
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(transformer.fromProgramDto(programDto)).thenReturn(program);

        // Act
        assertDoesNotThrow(() -> programService.updateProgramDetails(programDto));

        // Assert
        verify(programRepository, times(1)).save(program);
    }

    @Test
    void updateProgramDetails_Failure() {
        // Arrange
        ProgramDto programDto = mock(ProgramDto.class);
        when(programDto.getId()).thenReturn(1L);
        when(programRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> programService.updateProgramDetails(programDto));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void deleteProgram_Success() {
        // Arrange
        Program program = mock(Program.class);
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));

        // Act
        assertDoesNotThrow(() -> programService.deleteProgram(1L));

        // Assert
        verify(programRepository, times(1)).delete(program);
    }

    @Test
    void deleteProgram_Failure() {
        // Arrange
        when(programRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> programService.deleteProgram(1L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void getProgram_Success() {
        // Arrange
        Program program = mock(Program.class);
        ProgramDto programDto = mock(ProgramDto.class);
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(transformer.toProgramDto(program)).thenReturn(programDto);

        // Act
        ProgramDto result = programService.getProgram(1L);

        // Assert
        assertNotNull(result);
        verify(programRepository, times(1)).findById(1L);
    }

    @Test
    void getProgram_Failure() {
        // Arrange
        when(programRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> programService.getProgram(1L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void getAllPrograms_Success() {
        // Arrange
        List<Program> programs = Arrays.asList(mock(Program.class));
        ProgramDto programDto = mock(ProgramDto.class);
        when(programRepository.findAll()).thenReturn(programs);
        when(transformer.toProgramDto(any(Program.class))).thenReturn(programDto);

        // Act
        List<ProgramDto> result = programService.getAllPrograms();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getProgramsForLecturerId_Success() {
        // Arrange
        Lecturer lecturer = mock(Lecturer.class);
        Set<Program> programs = new HashSet<>(Collections.singletonList(mock(Program.class)));
        ProgramDto programDto = mock(ProgramDto.class);
        when(lecturerRepository.findById(1L)).thenReturn(Optional.of(lecturer));
        when(programRepository.findProgramsByLecturerId(1L)).thenReturn(programs);
        when(transformer.toProgramDto(any(Program.class))).thenReturn(programDto);

        // Act
        Set<ProgramDto> result = programService.getProgramsForLecturerId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getProgramsForLecturerId_Failure() {
        // Arrange
        when(lecturerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> programService.getProgramsForLecturerId(1L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void getFilteredPrograms_Success() {
        // Arrange
        Page<Program> programPage = new PageImpl<>(Collections.singletonList(mock(Program.class)));
        ProgramDto programDto = mock(ProgramDto.class);
        Pageable pageable = mock(Pageable.class);
        when(programRepository.findFilteredPrograms(anyString(), anyString(), anyString(), anyInt(), anyInt(), eq(pageable)))
                .thenReturn(programPage);
        when(transformer.toProgramDto(any(Program.class))).thenReturn(programDto);

        // Act
        Page<ProgramDto> result = programService.getFilteredPrograms("name", "description", "level", 10, 20, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}