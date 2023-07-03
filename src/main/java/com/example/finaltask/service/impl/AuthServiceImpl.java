package com.example.finaltask.service.impl;

import com.example.finaltask.model.dto.NewPasswordDTO;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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

  private final PasswordEncoder encoder;

  private final UserRepository userRepository;


  public AuthServiceImpl(PasswordEncoder passwordEncoder,
                         UserRepository userRepository) {
    this.encoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public boolean login(String userName, String password) {
    if (userRepository.findByEmail(userName).isEmpty()) {
      return false;
    }
    User user = userRepository.findByEmail(userName).orElseThrow();
    return encoder.matches(password,user.getPassword());
  }

  @Override
  public boolean register(RegisterReq registerReq, Role role) {
    System.out.println("есть ли reg " + userRepository.findByEmail(registerReq.getUsername()).isPresent());
    if (userRepository.findByEmail(registerReq.getUsername()).isPresent()) {
      return false;
    }
    User user = new User();
    user.setEmail(registerReq.getUsername());
    user.setPassword(encoder.encode(registerReq.getPassword()));
    user.setFirstName(registerReq.getFirstName());
    user.setLastName(registerReq.getLastName());
    user.setPhone(registerReq.getPhone());
    user.setRole(role);
    userRepository.save(user);
    return true;
  }
  public boolean changePassword(NewPasswordDTO newPasswordDto, String userName) { //todo
    if (userRepository.findByEmail(userName).isPresent()) {
      String encodedNewPassword = encoder.encode(newPasswordDto.getNewPassword());
      User user = userRepository.findByEmail(userName).orElseThrow();
      user.setPassword(encodedNewPassword);
      userRepository.save(user);
      return true;
    }
    log.info("Пользователь с именем {} не найден", userName);
    return false;
  }
}
