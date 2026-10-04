package lk.ac.iit.lecturer.api;

import jakarta.validation.Valid;
import lk.ac.iit.lecturer.dto.AiMatchRequestDto;
import lk.ac.iit.lecturer.dto.AiMatchResponseDto;
import lk.ac.iit.lecturer.dto.LecturerDto;
import lk.ac.iit.lecturer.dto.LecturerMultipartRequest;
import lk.ac.iit.lecturer.dto.LecturerRequest;
import lk.ac.iit.lecturer.rest.AiMatchClient;
import lk.ac.iit.lecturer.service.LecturerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/lecturers")
@CrossOrigin
public class LecturerApi {
    private final LecturerService service;
    private final AiMatchClient aiMatchClient;

    public LecturerApi(LecturerService service, AiMatchClient aiMatchClient) {
        this.service = service;
        this.aiMatchClient = aiMatchClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LecturerDto create(@Valid @RequestBody LecturerRequest request) {
        return service.create(request);
    }

    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public LecturerDto createMultipart(@Valid @ModelAttribute LecturerMultipartRequest request)
            throws java.io.IOException {
        return service.create(request.toRequest(), request.getPicture());
    }

    @GetMapping
    public List<LecturerDto> getAll(@RequestParam(required = false) String division,
                                    @RequestParam(required = false) Long instituteId) {
        return service.getAll(division, instituteId);
    }

    @GetMapping("/{id}")
    public LecturerDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/institutes/{instituteId}")
    public List<LecturerDto> getForInstitute(@PathVariable Long instituteId) {
        return service.getAll(null, instituteId);
    }

    @PutMapping("/{id}")
    public LecturerDto update(@PathVariable Long id, @Valid @RequestBody LecturerRequest request) {
        return service.update(id, request);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public LecturerDto updateMultipart(@PathVariable Long id,
                                       @Valid @ModelAttribute LecturerMultipartRequest request)
            throws java.io.IOException {
        return service.update(id, request.toRequest(), request.getPicture());
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long id, @Valid @RequestBody LecturerRequest request) {
        service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("/{id}/rating")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRating(@PathVariable Long id, @RequestParam int newRating) {
        service.updateRating(id, newRating);
    }

    @PatchMapping("/{id}/subscribe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSubscription(@PathVariable Long id, @RequestParam boolean subscribed) {
        service.setSubscribed(id, subscribed);
    }

    @PatchMapping("/{id}/assign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAssigned(@PathVariable Long id, @RequestParam boolean assign) {
        service.setAssigned(id, assign);
    }

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        service.deactivate(id);
    }

    @PostMapping("/ai-match")
    public AiMatchResponseDto predict(@RequestBody AiMatchRequestDto request,
                                      @AuthenticationPrincipal Jwt jwt) {
        return aiMatchClient.predict(request, jwt.getTokenValue());
    }

    @PostMapping("/ai-match/retrain")
    public Map<String, Object> retrain(@RequestBody List<Map<String, Object>> trainingData,
                                       @AuthenticationPrincipal Jwt jwt) {
        return aiMatchClient.retrain(trainingData, jwt.getTokenValue());
    }

    @GetMapping("/filter")
    public Page<LecturerDto> filter(
            @RequestParam(required = false) String division,
            @RequestParam(required = false) BigDecimal payRateLower,
            @RequestParam(required = false) BigDecimal payRateUpper,
            @RequestParam(required = false) String qualification,
            @RequestParam(required = false) Boolean isAssigned,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String globalSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getFiltered(division, payRateLower, payRateUpper, qualification,
                isAssigned, language, globalSearch, pageable);
    }
}
