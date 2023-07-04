package com.example.finaltask.service;

import com.example.finaltask.configuration.Role;
import com.example.finaltask.mapping.UserMapper;
import com.example.finaltask.model.dto.RegisterReq;
import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Transactional
@Service
public class UserService {


    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User addUser(RegisterReq req, Role role) {
        User user  = userMapper.toEntity(req);
        RegisterReq req1 = userMapper.toDto2(user);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(Integer id) {
    return userRepository.findById(id).orElseThrow();
    }
    public Optional<UserDTO> getUserByLogin(String login) {
        UserDTO userDTO = userMapper.toDto(userRepository.findByEmail(login).orElseThrow());
        return Optional.ofNullable(userDTO);
    }

    public void deleteUserById(Integer id) {
         userRepository.deleteById(id);
    }
    public User editUser(RegisterReq registerReq, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        user.setFirstName(registerReq.getFirstName());
        user.setPhone(registerReq.getPhone());
        user.setLastName(registerReq.getLastName());


        return userRepository.save(user);
    }

}
