package lk.ac.iit.lecturer;

import lk.ac.iit.lecturer.entity.Lecturer;
import lk.ac.iit.lecturer.repository.LecturerRepository;
import lk.ac.iit.lecturer.repository.QualificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false",
        "spring.flyway.enabled=true"
})
@AutoConfigureMockMvc
class LecturerServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private QualificationRepository qualificationRepository;

    @BeforeEach
    void createTestLecturer() {
        qualificationRepository.deleteAll();
        lecturerRepository.deleteAll();

        Lecturer lecturer = new Lecturer();
        lecturer.setName("Test Lecturer");
        lecturer.setDivision("Colombo");
        lecturer.setEmail("lecturer@example.test");
        lecturer = lecturerRepository.save(lecturer);
    }

    @Test
    void lecturerRoleCanUseProtectedRoutes() throws Exception {
        mockMvc.perform(get("/api/v1/lecturers")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_LECTURER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", is("lecturer@example.test")))
                .andExpect(jsonPath("$[0].password").doesNotExist());
    }

    @Test
    void apiRejectsRequestsWithoutBearerToken() throws Exception {
        mockMvc.perform(get("/api/v1/lecturers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void lecturerRoutesRejectInstituteRole() throws Exception {
        mockMvc.perform(get("/api/v1/lecturers")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_INSTITUTE"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void onlyInstituteRoleCanRetrainAiModel() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/api/v1/lecturers/ai-match/retrain")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_LECTURER")))
                        .contentType("application/json")
                        .content("[]"))
                .andExpect(status().isForbidden());
    }
}
