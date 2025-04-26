package lk.ac.iit.lecture_link.service.custom.impl;

import lk.ac.iit.lecture_link.dto.QualificationDto;
import lk.ac.iit.lecture_link.entity.Qualification;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.QualificationRepository;
import lk.ac.iit.lecture_link.service.util.Transformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QualificationServiceImplTest {

    @InjectMocks
    private QualificationServiceImpl qualificationService;

    @Mock
    private QualificationRepository qualificationRepository;

    @Mock
    private Transformer transformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveQualification_Success() {
        // Arrange
        QualificationDto qualificationDto = mock(QualificationDto.class);
        Qualification qualification = mock(Qualification.class);
        when(transformer.fromQualificationDto(qualificationDto)).thenReturn(qualification);
        when(qualificationRepository.save(qualification)).thenReturn(qualification);
        when(transformer.toQualificationDto(qualification)).thenReturn(qualificationDto);

        // Act
        QualificationDto result = qualificationService.saveQualification(qualificationDto);

        // Assert
        assertNotNull(result);
        verify(qualificationRepository, times(1)).save(qualification);
    }

    @Test
    void updateQualificationDetails_Success() {
        // Arrange
        QualificationDto qualificationDto = mock(QualificationDto.class);
        Qualification qualification = mock(Qualification.class);
        when(qualificationDto.getId()).thenReturn(1L);
        when(qualificationRepository.findById(1L)).thenReturn(Optional.of(qualification));
        when(transformer.fromQualificationDto(qualificationDto)).thenReturn(qualification);

        // Act
        assertDoesNotThrow(() -> qualificationService.updateQualificationDetails(qualificationDto));

        // Assert
        verify(qualificationRepository, times(1)).save(qualification);
    }

    @Test
    void updateQualificationDetails_Failure() {
        // Arrange
        QualificationDto qualificationDto = mock(QualificationDto.class);
        when(qualificationDto.getId()).thenReturn(1L);
        when(qualificationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> qualificationService.updateQualificationDetails(qualificationDto));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void deleteQualification_Success() {
        // Arrange
        Qualification qualification = mock(Qualification.class);
        when(qualificationRepository.findById(1L)).thenReturn(Optional.of(qualification));

        // Act
        assertDoesNotThrow(() -> qualificationService.deleteQualification(1L));

        // Assert
        verify(qualificationRepository, times(1)).delete(qualification);
    }

    @Test
    void deleteQualification_Failure() {
        // Arrange
        when(qualificationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> qualificationService.deleteQualification(1L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void getQualification_Success() {
        // Arrange
        Qualification qualification = mock(Qualification.class);
        QualificationDto qualificationDto = mock(QualificationDto.class);
        when(qualificationRepository.findById(1L)).thenReturn(Optional.of(qualification));
        when(transformer.toQualificationDto(qualification)).thenReturn(qualificationDto);

        // Act
        QualificationDto result = qualificationService.getQualification(1L);

        // Assert
        assertNotNull(result);
        verify(qualificationRepository, times(1)).findById(1L);
    }

    @Test
    void getQualification_Failure() {
        // Arrange
        when(qualificationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> qualificationService.getQualification(1L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void getAllQualifications_Success() {
        // Arrange
        List<Qualification> qualifications = Arrays.asList(mock(Qualification.class));
        QualificationDto qualificationDto = mock(QualificationDto.class);
        when(qualificationRepository.findAll()).thenReturn(qualifications);
        when(transformer.toQualificationDto(any(Qualification.class))).thenReturn(qualificationDto);

        // Act
        List<QualificationDto> result = qualificationService.getAllQualifications();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getFilteredQualifications_Success() {
        // Arrange
        Page<Qualification> qualificationPage = new PageImpl<>(Arrays.asList(mock(Qualification.class)));
        QualificationDto qualificationDto = mock(QualificationDto.class);
        Pageable pageable = mock(Pageable.class);
        when(qualificationRepository.findFilteredQualifications(anyString(), anyString(), anyInt(), anyString(), anyString(), eq(pageable)))
                .thenReturn(qualificationPage);
        when(transformer.toQualificationDto(any(Qualification.class))).thenReturn(qualificationDto);

        // Act
        Page<QualificationDto> result = qualificationService.getFilteredQualifications("name", "body", 10, "discipline", "level", pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}