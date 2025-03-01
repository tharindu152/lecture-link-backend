package lk.ac.iit.lecture_link.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto implements Serializable {
  private long id;
  private String role;
  private String token;
}
