package lk.ac.iit.lecture_link.api;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lk.ac.iit.lecture_link.converter.Role;
import lk.ac.iit.lecture_link.dto.AuthDto;
import lk.ac.iit.lecture_link.dto.InstituteDto;
import lk.ac.iit.lecture_link.dto.LecturerDto;
import lk.ac.iit.lecture_link.dto.request.InstituteReqDto;
import lk.ac.iit.lecture_link.dto.request.LecturerReqDto;
import lk.ac.iit.lecture_link.service.custom.InstituteService;
import lk.ac.iit.lecture_link.service.custom.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin
public class AdminHttpController {

    private final LecturerService lecturerService;
    private final InstituteService instituteService;
    private final SecretKey secretKey;

    @Value("${application.title}")
    private String issuer;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/register/institute", consumes = "multipart/form-data", produces = "application/json")
    public InstituteDto createNewInstitute(@ModelAttribute @Validated InstituteReqDto instituteReqDto) {
        return instituteService.saveInstitute(instituteReqDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/register/lecturer", consumes = "multipart/form-data", produces = "application/json")
    public LecturerDto createNewLecturer(@ModelAttribute @Validated LecturerReqDto lecturerReqDto) {
        return lecturerService.saveLecturer(lecturerReqDto);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public AuthDto login(@RequestBody Map<String, String> credentials) {

        String email = credentials.get("email");
        String password = credentials.get("password");

        AuthDto authDto = new AuthDto();

        try {
            LecturerDto lecturerDto = lecturerService.getLecturerByEmailAndPassword(email, password);
            if (Objects.nonNull(lecturerDto)) authDto.setId(lecturerDto.getId());
            authDto.setRole(Role.LECTURER.getRole());
            if(Objects.nonNull(lecturerDto) && !email.equals(lecturerDto.getEmail()) && !password.equals(lecturerDto.getPassword())){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            try {
                InstituteDto instituteDto = instituteService.getInstituteByEmailAndPassword(email, password);
                if (Objects.nonNull(instituteDto)) authDto.setId(instituteDto.getId());
                authDto.setRole(Role.INSTITUTE.getRole());
                if(Objects.nonNull(instituteDto) && !email.equals(instituteDto.getEmail()) && !password.equals(instituteDto.getPassword())){
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }
            } catch (Exception e1) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuer(issuer);
        jwtBuilder.issuedAt(new Date());
        LocalDateTime tokenExpTime = LocalDateTime.now().plusMinutes(180);
        Date expTime = Date.from(tokenExpTime.atZone(ZoneId.systemDefault()).toInstant());
        jwtBuilder.expiration(expTime);
        jwtBuilder.subject(email);
        jwtBuilder.signWith(secretKey);
        authDto.setToken(jwtBuilder.compact());

        return authDto;
    }
}
