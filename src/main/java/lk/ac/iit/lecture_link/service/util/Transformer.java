package lk.ac.iit.lecture_link.service.util;

import lk.ac.iit.lecture_link.dto.LecturerDto;
import lk.ac.iit.lecture_link.dto.LecturerReqDto;
import lk.ac.iit.lecture_link.entity.Lecturer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Transformer {

    private final ModelMapper mapper;

    public Transformer(ModelMapper mapper) {
        this.mapper = mapper;

//        mapper.typeMap(MultipartFile.class, Picture.class)
//                .setConverter(ctx -> null);
    }

    public Lecturer fromLecturerReqDto(LecturerReqDto lecturerReqDto){
        return mapper.map(lecturerReqDto, Lecturer.class);
    }

    public Lecturer fromLecturerDto(LecturerDto lecturerDto){
        return mapper.map(lecturerDto, Lecturer.class);
    }

    public LecturerDto toLecturerDto(Lecturer lecturer){
        return mapper.map(lecturer, LecturerDto.class);
    }

    public List<LecturerDto> toLectureDtoList(List<Lecturer> lecturerList){
        return lecturerList.stream().map(this::toLecturerDto).collect(Collectors.toList());
    }


}
