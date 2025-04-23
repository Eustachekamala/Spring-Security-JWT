package com.eustache.spring_security_ex.DTO;

public record StudentDTO(
        String username,
        String email,
        String password
) {
}
