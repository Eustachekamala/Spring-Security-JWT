package com.eustache.spring_security_ex.mapper;

import com.eustache.spring_security_ex.DTO.StudentDTO;
import com.eustache.spring_security_ex.DTO.StudentResponseDTO;
import com.eustache.spring_security_ex.models.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {

    //Convert StudentDTO to a Student entity
    public Student toStudentDTO(StudentDTO dto){
        var student = new Student();
        student.setUsername(dto.username());
        student.setEmail(dto.email());
        student.setPassword(dto.password());
        return student;
    }

    //Convert Author entity to AuthorResponseDTO
    public StudentResponseDTO toStudentResponseDTO(Student student){
        return new StudentResponseDTO(student.getUsername(), student.getEmail());
    }
}
