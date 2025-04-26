package lk.ac.iit.lecture_link.rest;

import lk.ac.iit.lecture_link.dto.AiMatchResponseDto;
import lk.ac.iit.lecture_link.dto.request.AiMatchRequestDto;

public interface AiMatchRestClient {
    AiMatchResponseDto getPrediction(AiMatchRequestDto requestDto);
    void retrainAiMatch();
}
