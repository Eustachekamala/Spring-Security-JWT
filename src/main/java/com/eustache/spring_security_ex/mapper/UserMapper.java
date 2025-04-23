package com.eustache.spring_security_ex.mapper;

import com.eustache.spring_security_ex.DTO.UserDTO;
import com.eustache.spring_security_ex.DTO.UserResponseDTO;
import com.eustache.spring_security_ex.models.Users;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    //Convert UserDTO to a Users entity
    public Users toStudentDTO(UserDTO dto){
        var student = new Users();
        student.setUsername(dto.username());
        student.setEmail(dto.email());
        student.setPassword(dto.password());
        return student;
    }

    //Convert Author entity to AuthorResponseDTO
    public UserResponseDTO toStudentResponseDTO(Users users){
        return new UserResponseDTO(users.getUsername(), users.getEmail());
    }
}
