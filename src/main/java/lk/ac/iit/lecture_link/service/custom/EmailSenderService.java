package lk.ac.iit.lecture_link.service.custom;

public interface EmailSenderService {
    void sendEmail(String toEmail, String subject, String body);
}
