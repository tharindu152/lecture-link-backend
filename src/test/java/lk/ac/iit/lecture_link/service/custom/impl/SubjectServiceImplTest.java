package lk.ac.iit.lecture_link.service.custom.impl;

import lk.ac.iit.lecture_link.dto.SubjectDto;
import lk.ac.iit.lecture_link.entity.Subject;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.SubjectRepository;
import lk.ac.iit.lecture_link.service.util.Transformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectServiceImplTest {

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private Transformer transformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveSubject_Success() {
        // Arrange
        SubjectDto subjectDto = mock(SubjectDto.class);
        Subject subject = mock(Subject.class);
        when(transformer.fromSubjectDto(subjectDto)).thenReturn(subject);
        when(subjectRepository.save(subject)).thenReturn(subject);
        when(transformer.toSubjectDto(subject)).thenReturn(subjectDto);

        // Act
        SubjectDto result = subjectService.saveSubject(subjectDto);

        // Assert
        assertNotNull(result);
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void updateSubjectDetails_Success() {
        // Arrange
        SubjectDto subjectDto = mock(SubjectDto.class);
        Subject subject = mock(Subject.class);
        when(subjectDto.getId()).thenReturn(1L);
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(transformer.fromSubjectDto(subjectDto)).thenReturn(subject);

        // Act
        assertDoesNotThrow(() -> subjectService.updateSubjectDetails(subjectDto));

        // Assert
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void updateSubjectDetails_Failure() {
        // Arrange
        SubjectDto subjectDto = mock(SubjectDto.class);
        when(subjectDto.getId()).thenReturn(1L);
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> subjectService.updateSubjectDetails(subjectDto));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void deleteSubject_Success() {
        // Arrange
        Subject subject = mock(Subject.class);
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        // Act
        assertDoesNotThrow(() -> subjectService.deleteSubject(1L));

        // Assert
        verify(subjectRepository, times(1)).delete(subject);
    }

    @Test
    void deleteSubject_Failure() {
        // Arrange
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> subjectService.deleteSubject(1L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void getSubject_Success() {
        // Arrange
        Subject subject = mock(Subject.class);
        SubjectDto subjectDto = mock(SubjectDto.class);
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(transformer.toSubjectDto(subject)).thenReturn(subjectDto);

        // Act
        SubjectDto result = subjectService.getSubject(1L);

        // Assert
        assertNotNull(result);
        verify(subjectRepository, times(1)).findById(1L);
    }

    @Test
    void getSubject_Failure() {
        // Arrange
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> subjectService.getSubject(1L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void getAllSubjects_Success() {
        // Arrange
        List<Subject> subjects = Arrays.asList(mock(Subject.class));
        SubjectDto subjectDto = mock(SubjectDto.class);
        when(subjectRepository.findAll()).thenReturn(subjects);
        when(transformer.toSubjectDto(any(Subject.class))).thenReturn(subjectDto);

        // Act
        List<SubjectDto> result = subjectService.getAllSubjects();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

}