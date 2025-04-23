package com.eustache.spring_security_ex.controller;

import com.eustache.spring_security_ex.DTO.UserDTO;
import com.eustache.spring_security_ex.DTO.UserResponseDTO;
import com.eustache.spring_security_ex.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String user() {
        return "Welcome to Spring Security Ex User";
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/users")
    public List<UserResponseDTO> student() {
        return userService.findAll();
    }

    @PostMapping("/register")
    public UserResponseDTO registerStudent(
            @RequestBody UserDTO userDTO
    ){
        return userService.registerStudent(userDTO);
    }

    @PostMapping("/login")
    public String login(
            @RequestBody UserDTO userDTO
    ){


        return  userService.verify(userDTO);
    }
}
