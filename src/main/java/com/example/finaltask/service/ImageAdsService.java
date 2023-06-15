package com.example.finaltask.service;

import com.example.finaltask.mapping.ImageMapper;
import com.example.finaltask.model.entity.Image;
import com.example.finaltask.model.entity.UserAvatar;
import com.example.finaltask.repository.AvatarRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageAdsService {

    private final AvatarRepository avatarRepository;

    private final ImageMapper imageMapper;

    public ImageAdsService(AvatarRepository avatarRepository, ImageMapper imageMapper) {
        this.avatarRepository = avatarRepository;
        this.imageMapper = imageMapper;
    }

    public UserAvatar saveImage(MultipartFile file) throws IOException {
        UserAvatar avatar = imageMapper.toEntity(file);
        return avatarRepository.save(avatar);
    }
}
