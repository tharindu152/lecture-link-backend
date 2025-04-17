package lk.ac.iit.lecture_link.api;

import lk.ac.iit.lecture_link.dto.EmailDto;
import lk.ac.iit.lecture_link.service.custom.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
@CrossOrigin
public class EmailHttpController {

    private final EmailSenderService emailSenderService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto request) {
        try {
            emailSenderService.sendEmail(request.getToEmail(), request.getSubject(), request.getBody());
            return ResponseEntity.ok("Email sent successfully to controller: " + request.getToEmail());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email. Error Controller: " + e.getMessage());
        }
    }
}
