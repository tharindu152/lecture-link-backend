package lk.ac.iit.lecture_link.rest.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ac.iit.lecture_link.dto.AiMatchResponseDto;
import lk.ac.iit.lecture_link.dto.request.AiMatchRequestDto;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.rest.AiMatchRestClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class AiMatchRestClientImpl implements AiMatchRestClient {

    private static final Logger log = LoggerFactory.getLogger(AiMatchRestClientImpl.class);

    @Value("${application.smart.match.api.url}")
    private String smartMatchUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final EntityManager em;

    @Override
    public AiMatchResponseDto getPrediction(AiMatchRequestDto requestDto) {
        try {
            log.info("Sending ai-match request to external API: {}", smartMatchUrl);
            String response = restTemplate.postForObject(smartMatchUrl + "/predict", requestDto, String.class);
            return objectMapper.readValue(response, AiMatchResponseDto.class);
        } catch (Exception e) {
            throw new AppException(500, "Failed to get ai-match from external API", e);
        }
    }


    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void retrainAiMatch() {
        log.info("Retrieving data for AI retraining...");

        String sqlQuery = "SELECT " +
                "i.current_rating AS institute_rating, " +
                "i.name AS institute_name, " +
                "i.division AS division, " +
                "i.status AS status, " +
                "p.name AS program, " +
                "p.hourly_pay_rate AS hourly_pay, " +
                "p.level AS level, " +
                "p.time_preference AS time_pref, " +
                "p.student_count AS student_count, " +
                "p.duration_in_days AS duration, " +
                "p.language AS language, " +
                "s.name AS subject, " +
                "s.no_of_credits AS credits, " +
                "l.id AS lecturer_id " +
                "FROM institute i " +
                "JOIN program p ON i.id = p.institute_id " +
                "JOIN program_subject ps ON p.id = ps.program_id " +
                "JOIN subject s ON ps.subject_id = s.id " +
                "LEFT JOIN lecturer l ON s.lecturer_id = l.id";

        List<Tuple> aiMatchData = em.createNativeQuery(sqlQuery, Tuple.class).getResultList();

        List<AiMatchData> aiMatchDataList = aiMatchData.stream()
                .map(tuple -> new AiMatchData(
                        tuple.get("program").toString().toLowerCase(),
                        (BigDecimal) tuple.get("hourly_pay"),
                        tuple.get("level").toString().toLowerCase(),
                        tuple.get("time_pref").toString().toLowerCase(),
                        ((Number) tuple.get("student_count")).intValue(),
                        tuple.get("subject").toString().toLowerCase(),
                        ((Number) tuple.get("credits")).intValue(),
                        ((Number) tuple.get("institute_rating")).doubleValue(),
                        ((Number) tuple.get("duration")).intValue(),
                        tuple.get("division").toString().toLowerCase(),
                        tuple.get("status").toString().toLowerCase(),
                        tuple.get("language").toString().toLowerCase(),
                        (BigInteger) tuple.get("lecturer_id")
                ))
                .collect(Collectors.toList());
        try {
            log.info("Sending retraining data to external API: {}", smartMatchUrl);
            restTemplate.postForObject(smartMatchUrl + "/retrain", aiMatchDataList, String.class);
            log.info("AI retraining request sent successfully.");
        } catch (Exception e) {
            throw new AppException(500, "AI retraining failed", e);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonPropertyOrder({"program", "hourly_pay", "level", "time_pref", "student_count", "subject", "credits", "institute_rating", "lecturer_id", "duration", "division", "status", "language"})
    static class AiMatchData {
        private String program;
        @JsonProperty("hourly_pay")
        private BigDecimal hourlyPay;
        private String level;
        @JsonProperty("time_pref")
        private String timePreference;
        @JsonProperty("student_count")
        private int studentCount;
        private String subject;
        @JsonProperty("credits")
        private int noOfCredits;
        @JsonProperty("institute_rating")
        private double instituteRating;
        @JsonProperty("duration")
        private int durationInDays;
        private String division;
        private String status;
        private String language;
        @JsonProperty("lecturer_id")
        private BigInteger lecturerId;
    }

}
