package lk.ac.iit.lecture_link.repository;

import java.util.List;
import lk.ac.iit.lecture_link.converter.Level;
import lk.ac.iit.lecture_link.entity.Institute;
import lk.ac.iit.lecture_link.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstituteRepository extends JpaRepository<Institute, Long> {

}
