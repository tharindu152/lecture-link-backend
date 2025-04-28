package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.entity.Lecturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    @Query("SELECT DISTINCT l FROM Lecturer l " +
            "LEFT JOIN l.qualifications q " +
            "WHERE (:division IS NULL OR LOWER(l.division) LIKE LOWER(CONCAT('%', :division, '%'))) " +
            "AND (:payRateLower IS NULL OR :payRateUpper IS NULL OR (l.hourlyPayRate BETWEEN :payRateLower AND :payRateUpper)) " +
            "AND (:qualification IS NULL OR LOWER(q.level) LIKE LOWER(CONCAT('%', :qualification, '%'))) " +
            "AND (:isAssigned IS NULL OR l.isAssigned = :isAssigned) " +
            "AND (:language IS NULL OR FUNCTION('FIND_IN_SET', :language, l.language) > 0) " +
            "AND (:globalSearch IS NULL OR LOWER(l.name) LIKE LOWER(CONCAT('%', :globalSearch, '%')) " +
            "AND LOWER(l.preference) LIKE LOWER(CONCAT('%', :globalSearch, '%')) " +
            "AND LOWER(l.fieldOfWork) LIKE LOWER(CONCAT('%', :globalSearch, '%')))")
    Page<Lecturer> findFilteredLecturers(
            @Param("division") String division,
            @Param("payRateLower") BigDecimal payRateLower,
            @Param("payRateUpper") BigDecimal payRateUpper,
            @Param("qualification") String qualification,
            @Param("isAssigned") Boolean isAssigned,
            @Param("language") String language,
            @Param("globalSearch") String globalSearch,
            Pageable pageable);


    @Query("SELECT DISTINCT l FROM Lecturer l " +
            "JOIN l.subjects s " +
            "JOIN s.programs p " +
            "WHERE p.institute.id = :instituteId")
    Set<Lecturer> findLecturersByInstituteId(@Param("instituteId") Long instituteId);

    Optional<Lecturer> getLecturerByEmailAndPassword(String email, String password);

    Optional<Lecturer> findLecturerByEmail(String email);

}
