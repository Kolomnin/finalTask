package com.example.finaltask.model.dto;

import com.example.finaltask.configuration.Role;
import lombok.Data;

@Data
public class SecurityUserDto {
    private Integer id;
    private String email;
    private String password;
    private Role role;
}