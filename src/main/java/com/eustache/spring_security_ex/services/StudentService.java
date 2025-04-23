package com.eustache.spring_security_ex.services;

import com.eustache.spring_security_ex.DTO.StudentDTO;
import com.eustache.spring_security_ex.DTO.StudentResponseDTO;
import com.eustache.spring_security_ex.mapper.StudentMapper;
import com.eustache.spring_security_ex.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    //Get all the students
    public List<StudentResponseDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toStudentResponseDTO)
                .collect(Collectors.toList());
    }

    //Create a student
    public StudentResponseDTO saveStudent(StudentDTO studentDTO) {
        var student = studentMapper.toStudentDTO(studentDTO);
        var savedStudent = studentRepository.save(student);
        return studentMapper.toStudentResponseDTO(savedStudent);
    }
}
