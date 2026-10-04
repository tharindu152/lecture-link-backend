package lk.ac.iit.lecturer.repository;

import lk.ac.iit.lecturer.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Optional<Lecturer> findByEmailIgnoreCase(String email);

    List<Lecturer> findByDivisionContainingIgnoreCase(String division);

    List<Lecturer> findByInstituteId(Long instituteId);
}
