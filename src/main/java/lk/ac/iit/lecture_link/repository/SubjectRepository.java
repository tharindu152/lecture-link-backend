package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s " +
            "WHERE s.name LIKE %:name% " +
            "AND s.description LIKE %:description% " +
            "AND s.noOfCredits = :noOfCredits " +
            "AND s.isAssigned = :isAssigned ")
    Page<Subject> findFilteredSubjects(
            @Param("name") String name,
            @Param("description") String description,
            @Param("noOfCredits") int noOfCredits,
            @Param("isAssigned") boolean isAssigned,
            Pageable pageable);

}
