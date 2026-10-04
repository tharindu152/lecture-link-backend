package lk.ac.iit.lecturer.rest;

import lk.ac.iit.lecturer.dto.AiMatchRequestDto;
import lk.ac.iit.lecturer.dto.AiMatchResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class AiMatchClient {
    private final RestClient restClient;

    public AiMatchClient(RestClient.Builder builder,
                         @Value("${application.ai-match.url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public AiMatchResponseDto predict(AiMatchRequestDto request, String accessToken) {
        return restClient.post().uri("/predict")
                .header("Authorization", "Bearer " + accessToken)
                .body(request).retrieve()
                .body(AiMatchResponseDto.class);
    }

    public Map<String, Object> retrain(List<Map<String, Object>> trainingData, String accessToken) {
        return restClient.post().uri("/retrain")
                .header("Authorization", "Bearer " + accessToken)
                .body(trainingData).retrieve()
                .body(new org.springframework.core.ParameterizedTypeReference<>() {});
    }
}
