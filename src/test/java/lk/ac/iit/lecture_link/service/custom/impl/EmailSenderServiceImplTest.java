package lk.ac.iit.lecture_link.service.custom.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class EmailSenderServiceImplTest {

    @InjectMocks
    private EmailSenderServiceImpl emailSenderService;

    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail_Success() {
        // Arrange
        String toEmail = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        assertDoesNotThrow(() -> emailSenderService.sendEmail(toEmail, subject, body));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendEmail_Failure() {
        // Arrange
        String toEmail = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        doThrow(new MailException("Mail sending failed") {}).when(mailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        assertDoesNotThrow(() -> emailSenderService.sendEmail(toEmail, subject, body));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}