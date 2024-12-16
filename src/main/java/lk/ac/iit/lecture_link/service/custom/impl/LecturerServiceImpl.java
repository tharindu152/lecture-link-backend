package lk.ac.iit.lecture_link.service.custom.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;

import lk.ac.iit.lecture_link.converter.Level;
import lk.ac.iit.lecture_link.converter.Status;
import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.exception.AppException;
import lk.ac.iit.lecture_link.repository.LecturerRepository;
import lk.ac.iit.lecture_link.service.custom.LecturerService;
import lk.ac.iit.lecture_link.service.util.Transformer;
import lk.ac.iit.lecture_link.dto.LecturerDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final Transformer transformer;
    private final Bucket bucket;

    public LecturerServiceImpl(LecturerRepository lecturerRepository, Transformer transformer, Bucket bucket) {
        this.lecturerRepository = lecturerRepository;
        this.transformer = transformer;
        this.bucket = bucket;
    }

    @Override
    public LecturerDto saveLecturer(LecturerDto lecturerDto) {
        Lecturer lecturer = transformer.fromLecturerDto(lecturerDto);
        lecturerRepository.save(lecturer);

        String signUrl = null;
        if (lecturerDto.getProfilePic() != null) {

            Blob blobRef = null;
            try {
                blobRef = bucket.create("lecturers/" + lecturer.getId(),
                        lecturerDto.getProfilePic().getInputStream(), lecturerDto.getProfilePic().getContentType());
            } catch (IOException e) {
                throw new AppException(500, "Failed dto upload the image", e);
            }
            signUrl = (blobRef.signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
            lecturer.setProfilePic(signUrl);
        }

      return transformer.toLecturerDto(lecturer);

    }


    @Override
    public void updateLecturerDetails(LecturerDto lecturerDto) {
        Lecturer currentLecturer = lecturerRepository.findById(lecturerDto.getId()).orElseThrow(() -> new AppException(404, "No lecturer associated with the id"));

        /* Let's remove old stuff */
        Blob blobRef = null;
        if (currentLecturer.getProfilePic() != null) {
            blobRef = bucket.get("lecturers/" + currentLecturer.getId());
        }

        Lecturer newLecturer = transformer.fromLecturerDto(lecturerDto);

        newLecturer = lecturerRepository.save(newLecturer);

        /* Let's check whether we have new stuff, if so let's persist them */
        if (lecturerDto.getProfilePic() != null) {
            newLecturer.setProfilePic("lecturers/" + newLecturer.getId());
        }

        try {
            if (lecturerDto.getProfilePic() != null) {
                bucket.create("lecturers/" + newLecturer.getId(), lecturerDto.getProfilePic().getInputStream(), lecturerDto.getProfilePic().getContentType());
            }else if (blobRef != null){
                blobRef.delete();
            }
        } catch (IOException e) {
            throw new AppException(500, "Failed dto update the image", e);
        }
    }

    @Override
    public void deleteLecturer(Long lecturerId) {
        if (!lecturerRepository.existsById(lecturerId)) throw new AppException(404, "No lecturer found from given id");
        lecturerRepository.deleteById(lecturerId);
    }

    @Override
    public LecturerDto getLecturerDetails(Long lecturerId) {

        Optional<Lecturer> optLecturer = lecturerRepository.findById(lecturerId);
        if (optLecturer.isEmpty()) throw new AppException(404, "No lecturer found");
        LecturerDto lecturerDto = transformer.toLecturerDto(optLecturer.get());
//        if (optLecturer.get().getProfilePic() != null) {
//            lecturerDto.setProfilePic(bucket.get(optLecturer.get().getProfilePic()).signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
//        }
        return lecturerDto;
    }

    @Override
    public List<LecturerDto> getLecturers(Status status) {

        List<Lecturer> lecturerList;
        if (status != null){
            lecturerList = lecturerRepository.findLecturerByStatus(status);
        } else {
            lecturerList = lecturerRepository.findAll();
        }
        return lecturerList.stream().map(l -> {
            LecturerDto lecturerTO = transformer.toLecturerDto(l);
//            if (l.getProfilePic() != null) {
//                lecturerTO.setProfilePic(bucket.get(l.getProfilePic()).signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
//            }
            return lecturerTO;
        }).collect(Collectors.toList());

    }
}
