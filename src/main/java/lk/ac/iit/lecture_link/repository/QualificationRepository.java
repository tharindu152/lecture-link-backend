package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.entity.Qualification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {

    @Query("SELECT q FROM Qualification q " +
            "WHERE q.name LIKE %:name% " +
            "AND q.awardingBody LIKE %:awardingBody% " +
            "AND q.durationInDays = :durationInDays " +
            "AND q.discipline LIKE %:discipline% " +
            "AND q.level LIKE %:level% ")
    Page<Qualification> findFilteredQualifications(
            @Param("name") String name,
            @Param("awardingBody") String awardingBody,
            @Param("durationInDays") int durationInDays,
            @Param("discipline") String discipline,
            @Param("level") String level,
            Pageable pageable);
}
