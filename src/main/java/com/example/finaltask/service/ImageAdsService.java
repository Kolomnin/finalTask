package com.example.finaltask.service;

import com.example.finaltask.mapping.ImageMapper;
import com.example.finaltask.model.entity.UserAvatar;
import com.example.finaltask.repository.AvatarRepository;
import com.example.finaltask.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageAdsService {

    private final AvatarRepository avatarRepository;

    private final ImageMapper imageMapper;

    private final UserRepository userRepository;

    public ImageAdsService(AvatarRepository avatarRepository, ImageMapper imageMapper, UserRepository userRepository) {
        this.avatarRepository = avatarRepository;
        this.imageMapper = imageMapper;
        this.userRepository = userRepository;
    }

    public UserAvatar saveImage(MultipartFile file,Authentication authentication) throws IOException {
        UserAvatar avatar = imageMapper.toEntity(file);
        avatar.setUser(userRepository.findByLogin(authentication.getName()));

        return avatarRepository.save(avatar);
    }
}
