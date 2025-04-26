package lk.ac.iit.lecture_link.security;

import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.service.custom.InstituteService;
import lk.ac.iit.lecture_link.service.custom.LecturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SecurityInterceptorTest {

    private SecurityInterceptor securityInterceptor;

    @Mock
    private SecretKey secretKey;

    @Mock
    private LecturerService lecturerService;

    @Mock
    private InstituteService instituteService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityInterceptor = new SecurityInterceptor(secretKey, lecturerService, instituteService);
    }

    @Test
    void preHandle_NoAuthorizationHeader_ThrowsUnauthorized() {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/v1/some-endpoint");
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> securityInterceptor.preHandle(request, response, null));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @Test
    void preHandle_ExcludedEndpoint_Success() throws Exception {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/v1/login");

        // Act
        boolean result = securityInterceptor.preHandle(request, response, null);

        // Assert
        assertTrue(result);
    }
}