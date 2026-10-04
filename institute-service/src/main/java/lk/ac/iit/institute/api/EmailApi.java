package lk.ac.iit.institute.api;

import jakarta.validation.Valid;
import lk.ac.iit.institute.dto.EmailDto;
import lk.ac.iit.institute.service.EmailSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@CrossOrigin
public class EmailApi {
    private final EmailSenderService service;

    public EmailApi(EmailSenderService service) {
        this.service = service;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public String send(@Valid @RequestBody EmailDto request) {
        service.sendEmail(request.toEmail(), request.subject(), request.body());
        return "Email sent successfully";
    }
}
