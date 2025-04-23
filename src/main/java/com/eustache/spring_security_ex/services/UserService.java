package com.eustache.spring_security_ex.services;

import com.eustache.spring_security_ex.DTO.UserDTO;
import com.eustache.spring_security_ex.DTO.UserResponseDTO;
import com.eustache.spring_security_ex.mapper.UserMapper;
import com.eustache.spring_security_ex.models.Users;
import com.eustache.spring_security_ex.models.StudentDetails;
import com.eustache.spring_security_ex.repositories.UserRepository;
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
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException(username);
        }
        return new StudentDetails(users);
    }

    public UserService(UserRepository userRepository, UserMapper userMapper, @Lazy AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //Get all the students
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toStudentResponseDTO)
                .collect(Collectors.toList());
    }

    //Create a student
    public UserResponseDTO registerStudent(UserDTO userDTO) {
        var student = userMapper.toStudentDTO(userDTO);
        student.setPassword(encoder.encode(student.getPassword()));
        var savedStudent = userRepository.save(student);
        return userMapper.toStudentResponseDTO(savedStudent);
    }

    public String verify(UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userDTO.username());
        }
        return "Failed";
    }
}
