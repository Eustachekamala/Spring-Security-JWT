package com.eustache.spring_security_ex.controller;

import com.eustache.spring_security_ex.DTO.StudentDTO;
import com.eustache.spring_security_ex.DTO.StudentResponseDTO;
import com.eustache.spring_security_ex.services.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String user() {
        return "Welcome to Spring Security Ex User";
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/students")
    public List<StudentResponseDTO> student() {
        return studentService.findAll();
    }

    @PostMapping("/students")
    public StudentResponseDTO createStudent(
            @RequestBody StudentDTO studentDTO
    ) {
        return studentService.saveStudent(studentDTO);
    }
}
