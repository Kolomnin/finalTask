package com.example.finaltask.service.impl;

import com.example.finaltask.configuration.Role;
import com.example.finaltask.model.dto.NewPasswordDTO;
import com.example.finaltask.model.dto.RegisterReq;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.UserRepository;
import com.example.finaltask.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Метод для входа пользователя.
     *
     * @param userName имя пользователя или адрес электронной почты пользователя
     * @param password пароль пользователя
     * @return {@code true}, если вход выполнен успешно, {@code false} в противном случае
     */
    @Override
    public boolean login(String userName, String password) {
        if (userRepository.findByEmail(userName).isEmpty()) {
            return false;
        }
        User user = userRepository.findByEmail(userName).orElseThrow();
        return encoder.matches(password, user.getPassword());
    }

    /**
     * Регистрирует нового пользователя с предоставленным запросом на регистрацию и ролью.
     *
     * @param registerReq запрос на регистрацию, содержащий информацию о пользователе
     * @param role        роль пользователя
     * @return true, если регистрация прошла успешно, иначе false
     */
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

    /**
     * Изменяет пароль пользователя.
     *
     * @param newPasswordDto Объект DTO, содержащий новый пароль.
     * @param userName Имя пользователя.
     * @return {@code true}, если пароль был успешно изменен, {@code false} в противном случае.
     */
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
