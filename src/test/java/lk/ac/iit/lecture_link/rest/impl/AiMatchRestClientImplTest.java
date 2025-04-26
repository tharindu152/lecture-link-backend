package lk.ac.iit.lecture_link.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ac.iit.lecture_link.dto.AiMatchResponseDto;
import lk.ac.iit.lecture_link.dto.request.AiMatchRequestDto;
import lk.ac.iit.lecture_link.exception.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AiMatchRestClientImplTest {

    @InjectMocks
    private AiMatchRestClientImpl aiMatchRestClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Logger logger;

    @Mock
    private Query query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPrediction_Success() throws Exception {
        // Arrange
        AiMatchRequestDto requestDto = new AiMatchRequestDto();
        String mockResponse = "{\"prediction\":\"success\"}";
        AiMatchResponseDto expectedResponse = new AiMatchResponseDto();
        when(restTemplate.postForObject(anyString(), eq(requestDto), eq(String.class))).thenReturn(mockResponse);
        when(objectMapper.readValue(mockResponse, AiMatchResponseDto.class)).thenReturn(expectedResponse);

        // Act
        AiMatchResponseDto actualResponse = aiMatchRestClient.getPrediction(requestDto);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate, times(1)).postForObject(anyString(), eq(requestDto), eq(String.class));
        verify(objectMapper, times(1)).readValue(mockResponse, AiMatchResponseDto.class);
    }

    @Test
    void getPrediction_Failure() {
        // Arrange
        AiMatchRequestDto requestDto = new AiMatchRequestDto();
        when(restTemplate.postForObject(anyString(), eq(requestDto), eq(String.class))).thenThrow(new RuntimeException("API error"));

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> aiMatchRestClient.getPrediction(requestDto));
        assertEquals(500, exception.getErrorCode());
        assertTrue(exception.getMessage().contains("Failed to get ai-match from external API"));
    }

    @Test
    void retrainAiMatch_Success() {
        // Arrange
        Tuple mockTuple = mock(Tuple.class);
        when(mockTuple.get("program")).thenReturn("Program A");
        when(mockTuple.get("hourly_pay")).thenReturn(BigDecimal.valueOf(50));
        when(mockTuple.get("level")).thenReturn("Level 1");
        when(mockTuple.get("time_pref")).thenReturn("Morning");
        when(mockTuple.get("student_count")).thenReturn(30);
        when(mockTuple.get("subject")).thenReturn("Math");
        when(mockTuple.get("credits")).thenReturn(3);
        when(mockTuple.get("institute_rating")).thenReturn(4.5);
        when(mockTuple.get("duration")).thenReturn(10);
        when(mockTuple.get("division")).thenReturn("Division A");
        when(mockTuple.get("status")).thenReturn("Active");
        when(mockTuple.get("language")).thenReturn("English");
        when(mockTuple.get("lecturer_id")).thenReturn(BigInteger.valueOf(1));

        List<Tuple> mockResultList = Arrays.asList(mockTuple);
        when(entityManager.createNativeQuery(anyString(), eq(Tuple.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockResultList);

        // Act
        aiMatchRestClient.retrainAiMatch();

        // Assert
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Tuple.class));
        verify(query, times(1)).getResultList();
        verify(restTemplate, times(1)).postForObject(anyString(), anyList(), eq(String.class));
    }
}