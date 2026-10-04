package lk.ac.iit.institute;

import lk.ac.iit.institute.entity.Institute;
import lk.ac.iit.institute.repository.InstituteRepository;
import lk.ac.iit.institute.repository.ProgramRepository;
import lk.ac.iit.institute.repository.SubjectRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false",
        "spring.flyway.enabled=true"
})
@AutoConfigureMockMvc
class InstituteServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private Institute institute;

    @BeforeEach
    void createTestInstitute() {
        programRepository.deleteAll();
        subjectRepository.deleteAll();
        instituteRepository.deleteAll();

        institute = new Institute();
        institute.setName("Test Institute");
        institute.setEmail("institute@example.test");
        institute.setDivision("Colombo");
        institute = instituteRepository.save(institute);
    }

    @Test
    void instituteRoleCanUseProtectedRoutes() throws Exception {
        mockMvc.perform(get("/api/v1/institutes")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_INSTITUTE"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", is("institute@example.test")))
                .andExpect(jsonPath("$[0].password").doesNotExist());
    }

    @Test
    void apiRejectsRequestsWithoutBearerToken() throws Exception {
        mockMvc.perform(get("/api/v1/institutes"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void instituteRoutesRejectLecturerRole() throws Exception {
        mockMvc.perform(get("/api/v1/institutes")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_LECTURER"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void instituteRoleCanCreateAndFilterSubjectsAndPrograms() throws Exception {
        var instituteToken = jwt().authorities(new SimpleGrantedAuthority("ROLE_INSTITUTE"));
        String subjectResponse = mockMvc.perform(post("/api/v1/subjects")
                        .with(instituteToken)
                        .contentType("application/json")
                        .content("""
                                {
                                  "name":"Business Analytics",
                                  "noOfCredits":3,
                                  "isAssigned":true,
                                  "lecturerId":42
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        long subjectId = new com.fasterxml.jackson.databind.ObjectMapper()
                .readTree(subjectResponse).get("id").asLong();

        mockMvc.perform(post("/api/v1/programs")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_INSTITUTE")))
                        .contentType("application/json")
                        .content("""
                                {
                                  "name":"Bachelor of Commerce",
                                  "level":"BACHELORS",
                                  "language":"ENGLISH",
                                  "timePreference":"WEEKEND",
                                  "instituteId":%d,
                                  "subjectIds":[%d]
                                }
                                """.formatted(institute.getId(), subjectId)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/subjects/filter")
                        .param("programLevel", "BACHELORS")
                        .param("credits", "3")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_INSTITUTE"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Business Analytics")));

        mockMvc.perform(get("/api/v1/programs/lecturer/42")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_INSTITUTE"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Bachelor of Commerce")));
    }
}
