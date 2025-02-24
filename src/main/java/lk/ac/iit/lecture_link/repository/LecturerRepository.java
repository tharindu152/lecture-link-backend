package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.entity.Lecturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Set;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    @Query("SELECT DISTINCT l FROM Lecturer l " +
            "LEFT JOIN l.qualifications q " +
            "WHERE (:district IS NULL OR l.district LIKE CONCAT('%', :district, '%')) " +
            "AND (:payRateUpper IS NULL OR :payRateLower IS NULL OR (:payRateUpper > l.payRate AND l.payRate > :payRateLower)) " +
            "AND (:qualification IS NULL OR q.level LIKE CONCAT('%', :qualification, '%')) " +
            "AND (:isAssigned IS NULL OR l.isAssigned = :isAssigned) " +
            "AND (:language IS NULL OR FUNCTION('FIND_IN_SET', :language, l.languages) > 0)")
    Page<Lecturer> findFilteredLecturers(
            @Param("district") String district,
            @Param("payRateLower") BigDecimal payRateLower,
            @Param("payRateUpper") BigDecimal payRateUpper,
            @Param("qualification") String qualification,
            @Param("isAssigned") Boolean isAssigned,
            @Param("language") String language,
            Pageable pageable);

    @Query("SELECT DISTINCT l FROM Lecturer l " +
            "JOIN l.subjects s " +
            "JOIN s.programs p " +
            "WHERE p.institute.id = :instituteId")
    Set<Lecturer> findLecturersByInstituteId(@Param("instituteId") Long instituteId);

}
