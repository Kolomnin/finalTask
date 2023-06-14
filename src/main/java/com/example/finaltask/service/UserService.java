package com.example.finaltask.service;

//@Service
//@RequiredArgsConstructor
//public class UserDTOService {
//
//    private final UserMapper userMapper;
//    private final UserRepository userRepository;
//
//    public User addUser(RegisterReq req, Role role) {
//        User user  = userMapper.toEntity(req);
//        RegisterReq req1 = userMapper.toDto2(user);
//        user.setRole(role);
//        return userRepository.save(user);
//    }
//
//    public User getUserById(Long id) {
//    return userRepository.findById(id);
//    }
//
//    public void deleteUserById(Integer id) {
//         userRepository.deleteById(id);
//    }
//    public User editUser(User user) {
//        return userRepository.save(user);
//    }
//
//
//
//}

import com.example.finaltask.model.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    UserDTO update(UserDTO user, String email);

    Optional<UserDTO> getUser(String name);

    UserDTO updateUser(UserDTO user, Integer id);

}
