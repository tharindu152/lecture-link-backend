package lk.ac.iit.lecture_link.repository;

import java.util.List;
import lk.ac.iit.lecture_link.converter.Level;
import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.entity.Subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
