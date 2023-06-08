package com.example.finaltask.service;

import com.example.finaltask.model.dto.RegisterReq;
import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserDTOService  {



   private final UserRepository userRepository;

    public UserDTOService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User addUser(UserDTO userdto) {
        User user = new User();
        user.setFirstName(userdto.getFirstName());
        user.setLastName(userdto.getLastName());
        user.setPhone(userdto.getPhone());

//        user.setId(userdto.ge());
//        user.setEmail(userdto.getEmail());
        return userRepository.save(user);
    }
    public User addUser1(RegisterReq userdto) {
        User user = new User();
        user.setFirstName(userdto.getFirstName());
        user.setLastName(userdto.getLastName());
        user.setPhone(userdto.getPhone());

//        user.setId(userdto.get());
//        user.setEmail(userdto.getEmail());
        return userRepository.save(user);
    }
    public User addUser2(User user) {
        return userRepository.save(user);
    }

}
