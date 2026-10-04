package lk.ac.iit.institute.repository;

import lk.ac.iit.institute.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByInstituteId(Long instituteId);
}
