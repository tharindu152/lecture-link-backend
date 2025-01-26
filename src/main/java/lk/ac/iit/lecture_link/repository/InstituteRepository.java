package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.entity.Institute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface InstituteRepository extends JpaRepository<Institute, Long> {

    @Query("SELECT i FROM Institute i " +
            "WHERE i.district LIKE %:district% " +
            "AND i.status = :status ")
    Page<Institute> findFilteredInstitutes(
            @Param("district") String district,
            @Param("status") String status,
            Pageable pageable);

    @Query("SELECT DISTINCT i FROM Institute i " +
            "JOIN i.programs p " +
            "JOIN p.subjects s " +
            "WHERE s.lecturer.id = :lecturerId")
    Set<Institute> findInstitutesByLecturerId(@Param("lecturerId") Long lecturerId);


}
