package com.example.finaltask.service.impl;

import com.example.finaltask.model.dto.NewPasswordDTO;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import com.example.finaltask.model.dto.RegisterReq;
import com.example.finaltask.configuration.Role;
import com.example.finaltask.service.AuthService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

  private final UserDetailsManager manager;

  private final PasswordEncoder encoder;


  public AuthServiceImpl(UserDetailsManager manager, PasswordEncoder passwordEncoder) {
    this.manager = manager;
    this.encoder = passwordEncoder;
  }

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
  public boolean changePassword(NewPasswordDTO newPasswordDto, String userName) { //todo
    if (manager.userExists(userName)) {
      String encodedNewPassword = encoder.encode(newPasswordDto.getNewPassword());
      System.out.println(encodedNewPassword+ "password");
      manager.changePassword(userName, encodedNewPassword);
//        user.setPassword(newPassword.getNewPassword());
      return true;
    }
    log.info("Пользователь с именем {} не найден", userName);
    return false;
  }
}
