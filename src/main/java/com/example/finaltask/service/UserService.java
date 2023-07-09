package com.example.finaltask.service;

import com.example.finaltask.configuration.Role;
import com.example.finaltask.mapping.UserMapper;
import com.example.finaltask.model.dto.RegisterReq;
import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Добавляет нового пользователя с предоставленным запросом на регистрацию и ролью.
     *
     * @param req  Запрос на регистрацию, содержащий данные пользователя.
     * @param role Роль, назначенная пользователю.
     * @return Недавно созданный объект пользователя.
     */
    public User addUser(RegisterReq req, Role role) {
        User user = userMapper.toEntity(req);
        RegisterReq req1 = userMapper.toDto2(user);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Получает пользователя по его идентификатору из базы данных.
     *
     * @param id Идентификатор пользователя, которого необходимо получить.
     * @return Пользователь с указанным ID.
     */
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    /**
     * Извлекает объект UserDTO при входе в систему.
     *
     * @param login логин пользователя для получения
     * @return необязательный элемент, содержащий объект UserDTO, или пустой необязательный элемент,
     * если пользователь не существует
     */
    public Optional<UserDTO> getUserByLogin(String login) {
        UserDTO userDTO = userMapper.toDto(userRepository.findByEmail(login).orElseThrow());
        return Optional.ofNullable(userDTO);
    }

    /**
     * Удаляет пользователя по его id.
     *
     * @param id идентификатор удаляемого пользователя
     */
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    /**
     * Обновляет сведения о пользователе на основе предоставленного запроса на регистрацию и
     * аутентификационной информации.
     *
     * @param registerReq Запрос на регистрацию, содержащий обновленные данные пользователя.
     * @param authentication Объект аутентификации, содержащий учетные данные пользователя.
     * @return Обновленный пользовательский объект.
     */
    public User editUser(RegisterReq registerReq, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        user.setFirstName(registerReq.getFirstName());
        user.setPhone(registerReq.getPhone());
        user.setLastName(registerReq.getLastName());

        return userRepository.save(user);
    }

}
