package com.example.finaltask.service;

import com.example.finaltask.model.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public interface UserService {

    UserDTO update(UserDTO user, String email);

    Optional<UserDTO> getUser();

    UserDTO updateUser(UserDTO user, Integer id);

    byte[] updateUserImage(Integer id, MultipartFile image) throws IOException;

}