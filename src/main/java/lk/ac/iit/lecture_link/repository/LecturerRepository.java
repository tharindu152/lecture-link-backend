package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.entity.Lecturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    @Query("SELECT l FROM Lecturer l " +
            "WHERE (:district IS NULL OR l.district LIKE %:district%) " +
            "AND (:status IS NULL OR l.status = :status) " +
            "AND (:isAssigned IS NULL OR l.isAssigned = :isAssigned) " +
            "AND (:languages IS NULL OR l.languages LIKE %:languages%) " +
            "AND (:qualificationLevel IS NULL OR EXISTS (" +
            "   SELECT s FROM Subject s WHERE s.lecturer = l AND s.id IN :subjectIds)) " +
            "AND (:qualificationLevel IS NULL OR EXISTS (" +
            "   SELECT q FROM Qualification q WHERE q.lecturer = l AND q.level IN :qualificationLevel))")
    Page<Lecturer> findFilteredLecturers(
            @Param("district") String district,
            @Param("status") String status,
            @Param("isAssigned") Boolean isAssigned,
            @Param("languages") String languages,
            @Param("subjectIds") Long subjectId,
            @Param("qualificationLevel") String qualificationLevel,
            Pageable pageable
    );

}
