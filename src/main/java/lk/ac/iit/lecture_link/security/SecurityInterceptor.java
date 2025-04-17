package lk.ac.iit.lecture_link.security;

import io.jsonwebtoken.*;
import lk.ac.iit.lecture_link.dto.InstituteDto;
import lk.ac.iit.lecture_link.dto.LecturerDto;
import lk.ac.iit.lecture_link.enums.Role;
import lk.ac.iit.lecture_link.service.custom.InstituteService;
import lk.ac.iit.lecture_link.service.custom.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor {

    private final SecretKey secretKey;
    private final LecturerService lecturerService;
    private final InstituteService instituteService;

    @Value("${application.title}")
    private String issuer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String endpoint = request.getServletPath();
        if (endpoint.equalsIgnoreCase("/api/v1/login")
                || endpoint.equalsIgnoreCase("/api/v1/register/lecturer")
                || endpoint.equalsIgnoreCase("/api/v1/register/institute")
                || endpoint.equalsIgnoreCase("/error")
                || endpoint.equalsIgnoreCase("/api/v1/email/send")
                || request.getMethod().equalsIgnoreCase("OPTIONS"))
            return true;

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.toUpperCase().startsWith("BEARER")) {
            String token = authorization.substring(7);
            JwtParserBuilder jwtParserBuilder = Jwts.parser().requireIssuer(issuer);
            try {
                Jws<Claims> jwt = jwtParserBuilder.verifyWith(secretKey).build().parseSignedClaims(token);
                Object email = jwt.getPayload().get("sub");
                if (Role.INSTITUTE.name().equals(request.getHeader("role"))) {
                    InstituteDto instituteDto = instituteService.findInstituteByEmail(email.toString());
                    if (Objects.nonNull(instituteDto) && email.equals(instituteDto.getEmail())) return true;
                } else if (Role.LECTURER.name().equals(request.getHeader("role"))) {
                    LecturerDto lecturerDto = lecturerService.findLecturerByEmail(email.toString());
                    if (Objects.nonNull(lecturerDto) && email.equals(lecturerDto.getEmail())) return true;
                }
            } catch (JwtException exp) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
