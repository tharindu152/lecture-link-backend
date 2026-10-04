package lk.ac.iit.lecturer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AiMatchResponseDto(
        @JsonProperty("predicted_lecturer_id") Long predictedLecturerId,
        @JsonProperty("top_3_recommendations") List<Recommendation> recommendations) {
    public record Recommendation(
            @JsonProperty("lecturer_id") Long lecturerId,
            double probability) {
    }
}
