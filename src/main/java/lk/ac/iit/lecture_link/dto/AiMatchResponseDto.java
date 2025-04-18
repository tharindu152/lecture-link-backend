package lk.ac.iit.lecture_link.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiMatchResponseDto {
    @JsonProperty("predicted_lecturer_id")
    private long matchedLecturerId;
    @JsonProperty("top_3_recommendations")
    private Recommendation[] recommendations;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Recommendation {
        @JsonProperty("lecturer_id")
        private long lecturerId;
        private double probability;
    }
}

