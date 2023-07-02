package com.example.finaltask.service.impl;

import com.example.finaltask.mapping.UserMapper;
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

 private final UserRepository userRepository;

  private final PasswordEncoder encoder;
  private final UserMapper userMapper;


  public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                         UserMapper userMapper) {
    this.userRepository = userRepository;
    this.encoder = passwordEncoder;
    this.userMapper=userMapper;
  }

  @Override
  public boolean login(String userName, String password) {
    System.out.println(userRepository.findByLogin(userName).isEmpty());
    if (userRepository.findByLogin(userName).isEmpty()) {

      return false;
    }
    com.example.finaltask.model.entity.User user = userRepository.findByLogin(userName).get();
      System.out.println(encoder.matches(password, user.getPassword()));
    return encoder.matches(password, user.getPassword());
  }

  @Override
  public boolean register(RegisterReq registerReq, Role role) {
    if (!userRepository.findByLogin(registerReq.getUsername()).isEmpty()) {
      return false;
    }
    com.example.finaltask.model.entity.User user = userMapper.toEntity(registerReq);
    user.setPassword(encoder.encode(user.getPassword()));
    user.setRole(role);
    userRepository.save(user);
    return true;
  }
//  public boolean changePassword(NewPasswordDTO newPasswordDto, String userName) { //todo
//    if (manager.userExists(userName)) {
//      String encodedNewPassword = encoder.encode(newPasswordDto.getNewPassword());
//      System.out.println(encodedNewPassword+ "password");
//      manager.changePassword(userName, encodedNewPassword);
////        user.setPassword(newPassword.getNewPassword());
//      return true;
//    }
//    log.info("Пользователь с именем {} не найден", userName);
//    return false;
//  }
}
