package com.example.finaltask.service;

import com.example.finaltask.mapping.ImageMapper;
import com.example.finaltask.model.entity.AdsImage;
import com.example.finaltask.model.entity.UserAvatar;
import com.example.finaltask.repository.AvatarRepository;
import com.example.finaltask.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;

    private final ImageMapper imageMapper;

    private final UserRepository userRepository;

    public AvatarService(AvatarRepository avatarRepository, ImageMapper imageMapper, UserRepository userRepository) {
        this.avatarRepository = avatarRepository;
        this.imageMapper = imageMapper;
        this.userRepository = userRepository;
    }

    public UserAvatar saveImage(MultipartFile file,Authentication authentication) throws IOException {
        UserAvatar avatar = imageMapper.toEntity(file);
        avatar.setUser(userRepository.findByLogin(authentication.getName()).orElseThrow());

        return avatarRepository.save(avatar);
    }

//    public String saveAds( MultipartFile image) {
//        AdsImage entity = new AdsImage();
//        try {
//            // код, который кладет картинку в entity
//            byte[] bytes = image.getBytes();
//            entity.setImage(bytes);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        entity.setId(UUID.randomUUID().toString());
//        // код сохранения картинки в БД
//        AdsEntity savedEntity = repository.saveAndFlush(entity);
//        return savedEntity.getId();
}
