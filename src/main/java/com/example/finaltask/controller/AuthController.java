package com.example.finaltask.controller;

import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.UserRepository;
import com.example.finaltask.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.finaltask.model.dto.LoginReq;
import com.example.finaltask.model.dto.RegisterReq;
import com.example.finaltask.configuration.Role;
import com.example.finaltask.service.AuthService;

import static com.example.finaltask.configuration.Role.USER;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AuthController {
    private final AuthService authService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    public AuthController(AuthService authService, UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authService = authService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Регистрирует пользователя с предоставленными учетными данными.
     *
     * @param req Запрос на вход, содержащий имя пользователя и пароль.
     * @return Объект ResponseEntity с зарегистрированным пользователем в случае успеха или со статусом Forbidden в случае сбоя входа.
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginReq req) {
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Регистрирует нового пользователя с предоставленной информацией.
     *
     * @param req Объект запроса, содержащий регистрационные данные пользователя.
     * @return A {@link ResponseEntity} с кодом состояния HTTP, указывающим на успешную регистрацию.
     * Если регистрация прошла успешно, возвращает статус HTTP 200 OK. Если регистрация не удалась, возвращает
     * Состояние HTTP 400 Bad Request.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq req) {
        Role role = req.getRole() == null ? USER : req.getRole();
        if (authService.register(req, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
