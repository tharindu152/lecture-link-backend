package lk.ac.iit.gatewayserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false"
})
@AutoConfigureWebTestClient
class GatewayserverApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void apiRejectsRequestsWithoutBearerToken() {
        webTestClient.get().uri("/api/v1/institutes")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void instituteRouteRejectsLecturerRole() {
        webTestClient.mutateWith(mockJwt()
                        .authorities(new SimpleGrantedAuthority("ROLE_LECTURER")))
                .get().uri("/api/v1/institutes")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void lecturerRouteRejectsInstituteRole() {
        webTestClient.mutateWith(mockJwt()
                        .authorities(new SimpleGrantedAuthority("ROLE_INSTITUTE")))
                .get().uri("/api/v1/lecturers")
                .exchange()
                .expectStatus().isForbidden();
    }
}
