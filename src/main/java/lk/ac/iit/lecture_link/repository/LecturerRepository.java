package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.converter.Level;
import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.converter.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    List<Lecturer> findLecturerByStatus(Status status);

}
