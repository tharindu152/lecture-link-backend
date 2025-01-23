package lk.ac.iit.lecture_link.security;

import io.jsonwebtoken.*;
import lk.ac.iit.lecture_link.repository.LecturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
@RequiredArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor {

    private final SecretKey secretKey;
    private final LecturerRepository lecturerRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /* Publicly authorize endpoints */
        String endpoint = request.getServletPath();
        /* anyone who is trying dto log in must be allowed*/
        if (endpoint.equalsIgnoreCase("/api/v1/lecturers/login")) return true;
        if (endpoint.equalsIgnoreCase("/error")) return true;

        // (1) Check for the authorization header
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            // (2) Check for the bearer token
            if (authorization.toUpperCase().startsWith("BEARER")) {
                // (3) Extract the token
                String token = authorization.substring(7);
                // (4) Parse the token
                JwtParserBuilder jwtParserBuilder = Jwts.parser().requireIssuer("dep-11");
                try {
                    //Verify the token with the secret key
                    Jws<Claims> jwt = jwtParserBuilder.verifyWith(secretKey).build().parseSignedClaims(token);
                    System.out.println(secretKey); //secret key is the same key we used dto create the JWT
                    // (5) Obtain user information
                    String username = jwt.getPayload().getSubject();
                    // (6) Verify user
                    if (username.equals("admin")) return true;
                } catch (JwtException exp) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
