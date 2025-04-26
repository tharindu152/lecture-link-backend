package lk.ac.iit.lecture_link.service.custom.impl;

import lk.ac.iit.lecture_link.dto.InstituteDto;
import lk.ac.iit.lecture_link.dto.request.InstituteReqDto;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstituteServiceImplTest {

    @InjectMocks
    private InstituteServiceImpl instituteService;

    @Mock
    private InstituteRepository instituteRepository;

    @Mock
    private LecturerRepository lecturerRepository;

    @Mock
    private Transformer transformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveInstitute_Success()  {
        // Arrange
        InstituteReqDto reqDto = mock(InstituteReqDto.class);
        Institute institute = mock(Institute.class);
        when(reqDto.getLogo()).thenReturn(null);
        when(transformer.fromInstituteReqDto(reqDto)).thenReturn(institute);
        when(instituteRepository.save(institute)).thenReturn(institute);
        when(transformer.toInstituteDto(institute)).thenReturn(new InstituteDto());

        // Act
        InstituteDto result = instituteService.saveInstitute(reqDto);

        // Assert
        assertNotNull(result);
        verify(instituteRepository, times(1)).save(institute);
    }

    @Test
    void saveInstitute_Failure() {
        // Arrange
        InstituteReqDto reqDto = mock(InstituteReqDto.class);
        when(reqDto.getLogo()).thenThrow(new AppException(500, "Error uploading logo"));

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> instituteService.saveInstitute(reqDto));
        assertEquals(500, exception.getErrorCode());
    }

    @Test
    void updateInstituteDetails_Success() {
        // Arrange
        InstituteReqDto reqDto = mock(InstituteReqDto.class);
        Institute currentInstitute = mock(Institute.class);
        Institute newInstitute = mock(Institute.class);
        when(instituteRepository.findById(anyLong())).thenReturn(Optional.of(currentInstitute));
        when(transformer.fromInstituteReqDto(reqDto)).thenReturn(newInstitute);

        // Act
        assertDoesNotThrow(() -> instituteService.updateInstituteDetails(reqDto));

        // Assert
        verify(instituteRepository, times(1)).save(newInstitute);
    }

    @Test
    void updateInstituteDetails_Failure() {
        // Arrange
        InstituteReqDto reqDto = mock(InstituteReqDto.class);
        when(instituteRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> instituteService.updateInstituteDetails(reqDto));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void deleteInstitute_Success() {
        // Arrange
        Institute institute = mock(Institute.class);
        when(instituteRepository.findById(anyLong())).thenReturn(Optional.of(institute));
        when(institute.getLogo()).thenReturn(null);

        // Act
        assertDoesNotThrow(() -> instituteService.deleteInstitute(1L));

        // Assert
        verify(instituteRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteInstitute_Failure() {
        // Arrange
        when(instituteRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> instituteService.deleteInstitute(1L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void getInstitute_Success() {
        // Arrange
        Institute institute = mock(Institute.class);
        when(instituteRepository.findById(anyLong())).thenReturn(Optional.of(institute));
        when(transformer.toInstituteDto(institute)).thenReturn(new InstituteDto());

        // Act
        InstituteDto result = instituteService.getInstitute(1L);

        // Assert
        assertNotNull(result);
        verify(instituteRepository, times(1)).findById(1L);
    }

    @Test
    void getInstitute_Failure() {
        // Arrange
        when(instituteRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> instituteService.getInstitute(1L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void getAllInstitutes_Success() {
        // Arrange
        List<Institute> institutes = Arrays.asList(mock(Institute.class));
        when(instituteRepository.findAll()).thenReturn(institutes);
        when(transformer.toInstituteDto(any(Institute.class))).thenReturn(new InstituteDto());

        // Act
        List<InstituteDto> result = instituteService.getAllInstitutes();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getInstitutesForLecturerId_Success() {
        // Arrange
        Lecturer lecturer = mock(Lecturer.class);
        Set<Institute> institutes = new HashSet<>(Arrays.asList(mock(Institute.class)));
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.of(lecturer));
        when(instituteRepository.findInstitutesByLecturerId(anyLong())).thenReturn(institutes);
        when(transformer.toInstituteDto(any(Institute.class))).thenReturn(new InstituteDto());

        // Act
        Set<InstituteDto> result = instituteService.getInstitutesForLecturerId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getInstitutesForLecturerId_Failure() {
        // Arrange
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> instituteService.getInstitutesForLecturerId(1L));
        assertEquals(404, exception.getErrorCode());
    }
}