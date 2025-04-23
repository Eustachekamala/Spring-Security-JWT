package com.eustache.spring_security_ex.services;

import com.eustache.spring_security_ex.DTO.StudentDTO;
import com.eustache.spring_security_ex.DTO.StudentResponseDTO;
import com.eustache.spring_security_ex.mapper.StudentMapper;
import com.eustache.spring_security_ex.models.Student;
import com.eustache.spring_security_ex.models.StudentDetails;
import com.eustache.spring_security_ex.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            throw new UsernameNotFoundException(username);
        }
        return new StudentDetails(student);
    }

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, @Lazy AuthenticationManager authenticationManager, JWTService jwtService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //Get all the students
    public List<StudentResponseDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toStudentResponseDTO)
                .collect(Collectors.toList());
    }

    //Create a student
    public StudentResponseDTO registerStudent(StudentDTO studentDTO) {
        var student = studentMapper.toStudentDTO(studentDTO);
        student.setPassword(encoder.encode(student.getPassword()));
        var savedStudent = studentRepository.save(student);
        return studentMapper.toStudentResponseDTO(savedStudent);
    }

    public String verify(StudentDTO studentDTO) {
        Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(studentDTO.username(), studentDTO.password())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(studentDTO.username());
        }
        return "Failed";
    }
}
