package lk.ac.iit.lecture_link.dto;

import lombok.Data;

@Data
public class EmailDto {
    private String toEmail;
    private String subject;
    private String body;
}