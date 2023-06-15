package com.example.finaltask.service;

import com.example.finaltask.configuration.Role;
import com.example.finaltask.mapping.UserMapper;
import com.example.finaltask.model.dto.ChangeUserChar;
import com.example.finaltask.model.dto.RegisterReq;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

    public void deleteUserById(Integer id) {
         userRepository.deleteById(id);
    }
    public User editUser(ChangeUserChar character, Authentication authentication) {
        User user = userRepository.findByLogin(authentication.getName());

        user.setFirstName(character.getFirstName());
        user.setPhone(character.getPhone());
        user.setLastName(character.getLastName());


        return userRepository.save(user);
    }

}
