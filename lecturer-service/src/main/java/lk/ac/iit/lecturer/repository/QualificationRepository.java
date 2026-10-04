package lk.ac.iit.lecturer.repository;

import lk.ac.iit.lecturer.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    List<Qualification> findByLecturerId(Long lecturerId);
}
