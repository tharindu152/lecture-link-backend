package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.entity.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    @Query("SELECT p FROM Program p " +
            "WHERE p.name LIKE %:name% " +
            "AND p.description LIKE %:description% " +
            "AND p.level = :level " +
            "AND p.durationInDays = :durationInDays " +
            "AND p.studentCount = :studentCount ")
    Page<Program> findFilteredPrograms(
            @Param("name") String name,
            @Param("description") String description,
            @Param("level") String level,
            @Param("durationInDays") int durationInDays,
            @Param("studentCount") int studentCount,
            Pageable pageable);

    @Query("SELECT DISTINCT p FROM Program p " +
            "JOIN p.subjects s " +
            "WHERE s.lecturer.id = :lecturerId")
    Set<Program> findProgramsByLecturerId(@Param("lecturerId") Long lecturerId);


}
