package com.example.finaltask.service.impl;

import com.example.finaltask.mapping.UserMapper;
import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.UserRepository;
import com.example.finaltask.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO update(UserDTO userDTO, String email) {
        User updatedUser = UserMapper.INSTANCE.userDtoToUser(userDTO);
        log.info("Update user: " + updatedUser);
        return UserMapper.INSTANCE.userToUserDto(userRepository.save(updatedUser));
    }

    @Override
    public Optional<UserDTO> getUser(String email) {
        log.info("Get user: " + email);
        return userRepository.findUserByEmail(email).map(UserMapper.INSTANCE::userToUserDto);
    }

    @Override
    public UserDTO updateUser(UserDTO user, Integer id) {
        log.info("Update user: " + user);
        if (!userRepository.existsById(Long.valueOf(id))) {
            throw new IllegalArgumentException("User not found");
        }
        return UserMapper.INSTANCE.userToUserDto(
                userRepository.save(UserMapper.INSTANCE.userDtoToUser(user))
        );
    }
}