package edu.java.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDto {
    private Long userId;
    private String firstname;
    private String surname;
}
