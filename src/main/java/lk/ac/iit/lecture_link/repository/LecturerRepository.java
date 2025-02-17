package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.entity.Lecturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

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

    @Query("SELECT DISTINCT l FROM Lecturer l " +
            "JOIN l.subjects s " +
            "JOIN s.programs p " +
            "WHERE p.institute.id = :instituteId")
    Set<Lecturer> findLecturersByInstituteId(@Param("instituteId") Long instituteId);

}
