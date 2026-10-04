package lk.ac.iit.institute.repository;

import lk.ac.iit.institute.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByLecturerId(Long lecturerId);

    List<Subject> findByNameContainingIgnoreCase(String name);
}
