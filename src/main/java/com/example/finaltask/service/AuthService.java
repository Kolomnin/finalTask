package com.example.finaltask.service;

import com.example.finaltask.configuration.Role;
import com.example.finaltask.model.dto.NewPasswordDTO;
import com.example.finaltask.model.dto.RegisterReq;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);

    boolean changePassword(NewPasswordDTO newPasswordDTO, String userName);
}
