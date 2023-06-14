package com.example.finaltask.service.impl;

import com.example.finaltask.configuration.Role;
import com.example.finaltask.model.dto.NewPasswordDTO;
import com.example.finaltask.model.dto.RegisterReq;
import com.example.finaltask.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;

    @Override
    public boolean login(String userName, String password) {
        System.out.println(userName);
        if (!manager.userExists(userName)) {

            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(role.name())
                        .build());
        return true;
    }

    @Override
    public boolean changePassword(NewPasswordDTO newPasswordDTO, String userName) {
        if (manager.userExists(userName)) {
            String encodedNewPassword = encoder.encode(newPasswordDTO.getNewPassword());
            manager.changePassword(userName, encodedNewPassword);
            return true;
        }
        log.info("Пользователь с именем {} не найден", userName);
        return false;
    }

}
