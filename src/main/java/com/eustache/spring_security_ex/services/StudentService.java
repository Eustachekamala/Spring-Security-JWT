package com.eustache.spring_security_ex.services;

import com.eustache.spring_security_ex.DTO.StudentDTO;
import com.eustache.spring_security_ex.DTO.StudentResponseDTO;
import com.eustache.spring_security_ex.mapper.StudentMapper;
import com.eustache.spring_security_ex.models.Student;
import com.eustache.spring_security_ex.models.StudentDetails;
import com.eustache.spring_security_ex.repositories.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            throw new UsernameNotFoundException(username);
        }
        return new StudentDetails(student);
    }

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
