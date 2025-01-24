package lk.ac.iit.lecture_link.service.util;

import lk.ac.iit.lecture_link.dto.*;
import lk.ac.iit.lecture_link.dto.request.InstituteReqDto;
import lk.ac.iit.lecture_link.dto.request.LecturerReqDto;
import lk.ac.iit.lecture_link.entity.Institute;
import lk.ac.iit.lecture_link.entity.Lecturer;
import lk.ac.iit.lecture_link.entity.Program;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Transformer {

    private final ModelMapper modelMapper;

    public Transformer(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Lecturer fromLecturerReqDto(LecturerReqDto lecturerReqDto){
        return modelMapper.map(lecturerReqDto, Lecturer.class);
    }

    public Lecturer fromLecturerDto(LecturerDto lecturerDto){
        return modelMapper.map(lecturerDto, Lecturer.class);
    }

    public LecturerDto toLecturerDto(Lecturer lecturer){
        return modelMapper.map(lecturer, LecturerDto.class);
    }

    public Institute fromInstituteReqDto(InstituteReqDto instituteReqDto){
        return modelMapper.map(instituteReqDto, Institute.class);
    }

    public Institute fromInstituteDto(InstituteDto instituteDto){
        return modelMapper.map(instituteDto, Institute.class);
    }

    public InstituteDto toInstituteDto(Institute institute){
        return modelMapper.map(institute, InstituteDto.class);
    }

    public Program fromProgramDto(ProgramDto programDto){
        return modelMapper.map(programDto, Program.class);
    }

    public ProgramDto toProgramDto(Program program){
        return modelMapper.map(program, ProgramDto.class);
    }

}
