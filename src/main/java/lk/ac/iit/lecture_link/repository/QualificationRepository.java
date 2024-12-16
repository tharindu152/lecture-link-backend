package lk.ac.iit.lecture_link.repository;

import java.util.List;
import lk.ac.iit.lecture_link.converter.Level;
import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.entity.Qualification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {

}
