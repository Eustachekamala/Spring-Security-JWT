package com.eustache.spring_security_ex.DTO;

public record UserDTO(
        String username,
        String email,
        String password
) {
}
