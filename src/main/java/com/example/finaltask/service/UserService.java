package com.example.finaltask.service;

import com.example.finaltask.configuration.Role;
import com.example.finaltask.mapping.UserMapper;
import com.example.finaltask.model.dto.RegisterReq;
import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Transactional
@Service
public class UserService {


    private final UserMapper userMapper;
    private final UserRepository userRepository;


    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }


    public User addUser(RegisterReq req, Role role) {
        User user  = userMapper.toEntity(req);
        RegisterReq req1 = userMapper.toDto2(user);
        user.setRole(role);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
    return userRepository.findById(id);
    }
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

//    public Optional<UserDTO> getUser(String name) {
//        log.info("Get user: " + name);
//        return userRepository.findByLogin(name);
//    }
    public void deleteUserById(Integer id) {
         userRepository.deleteById(id);
    }
    public User editUser(RegisterReq registerReq, Authentication authentication) {
        User user = userRepository.findByLogin(authentication.getName());

        user.setFirstName(registerReq.getFirstName());
        user.setPhone(registerReq.getPhone());
        user.setLastName(registerReq.getLastName());


        return userRepository.save(user);
    }

}
