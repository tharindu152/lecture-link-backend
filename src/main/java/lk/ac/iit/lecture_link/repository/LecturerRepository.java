package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.entity.Lecturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    @Query("SELECT l FROM Lecturer l " +
            "WHERE l.district LIKE %:district% " +
            "AND l.status = :status " +
            "AND l.languages LIKE %:languages%")
    Page<Lecturer> findFilteredLecturers(
            @Param("district") String district,
            @Param("status") String status,
            @Param("languages") String languages,
            Pageable pageable);
}
