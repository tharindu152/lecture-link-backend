package lk.ac.iit.lecture_link.repository;

import lk.ac.iit.lecture_link.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    void deletePictureByLecturer_Id(Long lecturerId);
}
