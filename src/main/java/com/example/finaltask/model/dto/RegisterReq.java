package com.example.finaltask.model.dto;

import com.example.finaltask.configuration.Role;
import lombok.Data;

@Data
public class RegisterReq {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
