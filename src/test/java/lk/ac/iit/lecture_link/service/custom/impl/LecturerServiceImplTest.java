package lk.ac.iit.lecture_link.service.custom.impl;

import lk.ac.iit.lecture_link.dto.LecturerDto;
import lk.ac.iit.lecture_link.dto.request.LecturerReqDto;
import lk.ac.iit.lecture_link.entity.Institute;
import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.InstituteRepository;
import lk.ac.iit.lecture_link.repository.LecturerRepository;
import lk.ac.iit.lecture_link.service.util.Transformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LecturerServiceImplTest {

    @InjectMocks
    private LecturerServiceImpl lecturerService;

    @Mock
    private LecturerRepository lecturerRepository;

    @Mock
    private InstituteRepository instituteRepository;

    @Mock
    private Transformer transformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveLecturer_Success() {
        // Arrange
        LecturerReqDto reqDto = mock(LecturerReqDto.class);
        Lecturer lecturer = mock(Lecturer.class);
        when(reqDto.getPicture()).thenReturn(null);
        when(transformer.fromLecturerReqDto(reqDto)).thenReturn(lecturer);
        when(lecturerRepository.save(lecturer)).thenReturn(lecturer);
        when(transformer.toLecturerDto(lecturer)).thenReturn(new LecturerDto());

        // Act
        LecturerDto result = lecturerService.saveLecturer(reqDto);

        // Assert
        assertNotNull(result);
        verify(lecturerRepository, times(1)).save(lecturer);
    }

    @Test
    void saveLecturer_Failure()  {
        // Arrange
        LecturerReqDto reqDto = mock(LecturerReqDto.class);
        when(reqDto.getPicture()).thenThrow(new AppException(500, "Failed to upload"));

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> lecturerService.saveLecturer(reqDto));
        assertEquals(500, exception.getErrorCode());
    }

    @Test
    void updateLecturerDetails_Success() {
        // Arrange
        LecturerReqDto reqDto = mock(LecturerReqDto.class);
        Lecturer currentLecturer = mock(Lecturer.class);
        Lecturer newLecturer = mock(Lecturer.class);
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.of(currentLecturer));
        when(transformer.fromLecturerReqDto(reqDto)).thenReturn(newLecturer);

        // Act
        assertDoesNotThrow(() -> lecturerService.updateLecturerDetails(reqDto));

        // Assert
        verify(lecturerRepository, times(1)).save(newLecturer);
    }

    @Test
    void updateLecturerDetails_Failure() {
        // Arrange
        LecturerReqDto reqDto = mock(LecturerReqDto.class);
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> lecturerService.updateLecturerDetails(reqDto));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void deleteLecturer_Success() {
        // Arrange
        Lecturer lecturer = mock(Lecturer.class);
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.of(lecturer));
        when(lecturer.getPicture()).thenReturn(null);

        // Act
        assertDoesNotThrow(() -> lecturerService.deleteLecturer(1L));

        // Assert
        verify(lecturerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteLecturer_Failure() {
        // Arrange
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> lecturerService.deleteLecturer(1L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void getLecturer_Success() {
        // Arrange
        Lecturer lecturer = mock(Lecturer.class);
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.of(lecturer));
        when(transformer.toLecturerDto(lecturer)).thenReturn(new LecturerDto());

        // Act
        LecturerDto result = lecturerService.getLecturer(1L);

        // Assert
        assertNotNull(result);
        verify(lecturerRepository, times(1)).findById(1L);
    }

    @Test
    void getLecturer_Failure() {
        // Arrange
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> lecturerService.getLecturer(1L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void getLecturersForInstituteId_Success() {
        // Arrange
        Institute institute = mock(Institute.class);
        Set<Lecturer> lecturers = new HashSet<>(Collections.singletonList(mock(Lecturer.class)));
        when(instituteRepository.findById(anyLong())).thenReturn(Optional.of(institute));
        when(lecturerRepository.findLecturersByInstituteId(anyLong())).thenReturn(lecturers);
        when(transformer.toLecturerDto(any(Lecturer.class))).thenReturn(new LecturerDto());

        // Act
        Set<LecturerDto> result = lecturerService.getLecturersForInstituteId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getLecturersForInstituteId_Failure() {
        // Arrange
        when(instituteRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> lecturerService.getLecturersForInstituteId(1L));
        assertEquals(404, exception.getErrorCode());
    }
}