package lk.ac.iit.lecturer.api;

import jakarta.validation.Valid;
import lk.ac.iit.lecturer.dto.EmailDto;
import lk.ac.iit.lecturer.service.EmailSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/v1/email", "/api/v1/lecturers/email"})
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
