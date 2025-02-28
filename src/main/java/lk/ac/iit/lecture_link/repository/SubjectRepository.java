package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query(value = "SELECT DISTINCT s.id, s.name, p.level, s.noOfCredits, p.studentCount, p.durationInDays, i.district, p.payment " +
            "FROM Subject s " +
            "JOIN s.programs p " +
            "JOIN p.institute i " +
            "WHERE (:district IS NULL OR i.district LIKE CONCAT('%', :district, '%')) " +
            "  AND (:programLevel IS NULL OR p.level LIKE CONCAT('%', :programLevel, '%')) " +
            "  AND (:credits IS NULL OR s.noOfCredits = :credits) " +
            "  AND (:paymentUpper IS NULL OR :paymentLower IS NULL OR (p.payment > :paymentLower AND p.payment < :paymentUpper)) " +
            "  AND (:durationUpper IS NULL OR :durationLower IS NULL OR (p.durationInDays > :durationLower AND p.durationInDays < :durationUpper)) " +
            "  AND (:studentUpper IS NULL OR :studentLower IS NULL OR (p.studentCount > :studentLower AND p.studentCount < :studentUpper)) " +
            "  AND (:globalSearch IS NULL OR s.name LIKE CONCAT('%', :globalSearch, '%') OR s.description LIKE CONCAT('%', :globalSearch, '%'))",
            countQuery = "SELECT COUNT(DISTINCT s) " +
                    "FROM Subject s " +
                    "JOIN s.programs p " +
                    "JOIN p.institute i " +
                    "WHERE (:district IS NULL OR i.district LIKE CONCAT('%', :district, '%')) " +
                    "  AND (:programLevel IS NULL OR p.level LIKE CONCAT('%', :programLevel, '%')) " +
                    "  AND (:credits IS NULL OR s.noOfCredits = :credits) " +
                    "  AND (:paymentUpper IS NULL OR :paymentLower IS NULL OR (p.payment > :paymentLower AND p.payment < :paymentUpper)) " +
                    "  AND (:durationUpper IS NULL OR :durationLower IS NULL OR (p.durationInDays > :durationLower AND p.durationInDays < :durationUpper)) " +
                    "  AND (:studentUpper IS NULL OR :studentLower IS NULL OR (p.studentCount > :studentLower AND p.studentCount < :studentUpper)) " +
                    "  AND (:globalSearch IS NULL OR s.name LIKE CONCAT('%', :globalSearch, '%') OR s.description LIKE CONCAT('%', :globalSearch, '%'))")
    Page<Object[]> findFilteredSubjects(
            @Param("district") String district,
            @Param("programLevel") String programLevel,
            @Param("credits") Integer credits,
            @Param("paymentLower") BigDecimal paymentLower,
            @Param("paymentUpper") BigDecimal paymentUpper,
            @Param("durationLower") Integer durationLower,
            @Param("durationUpper") Integer durationUpper,
            @Param("studentLower") Integer studentLower,
            @Param("studentUpper") Integer studentUpper,
            @Param("globalSearch") String globalSearch,
            Pageable pageable
    );



    List<Subject> findSubjectByLecturer(Lecturer lecturer);

}
