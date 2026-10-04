package lk.ac.iit.institute.repository;

import lk.ac.iit.institute.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Optional<Institute> findByEmailIgnoreCase(String email);

    List<Institute> findByDivisionContainingIgnoreCase(String division);
}
