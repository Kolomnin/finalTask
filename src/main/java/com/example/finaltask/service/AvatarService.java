package com.example.finaltask.service;

import com.example.finaltask.mapping.ImageMapper;
import com.example.finaltask.model.entity.AdsImage;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.model.entity.UserAvatar;
import com.example.finaltask.repository.AvatarRepository;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;
@Slf4j
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
        avatar.setUser(userRepository.findByEmail(authentication.getName()).orElseThrow());

        return avatarRepository.save(avatar);
    }

//    public byte[] saveAvatar(String email, MultipartFile file) throws IOException {
//        Integer id = userRepository.findByEmail(email).get().getId();
//        log.info("Was invoked method to upload photo to user with id {}", id);
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("File is empty");
//        }
//        if (!userRepository.existsById(id)) {
//            throw new IllegalArgumentException("User not found");
//        }
//        UserAvatar userAvatar = new UserAvatar();
//        userAvatar.setBytes(file.getBytes());
//        avatarRepository.save(userAvatar);
//        return userAvatar.getBytes();
//    }
public byte[] saveAvatar(String email, MultipartFile file) throws IOException {
    UserAvatar imageToSave;

    if (file.isEmpty()) {
        throw new IllegalArgumentException("File is empty");
    }
    Integer id = userRepository.findByEmail(email).get().getId();
    Optional<UserAvatar> existingAvatar = avatarRepository.findById(1);

    if (existingAvatar.isEmpty()) {
        imageToSave = new UserAvatar();
        imageToSave.setBytes(file.getBytes());
        imageToSave.setUser(userRepository.findByEmail(email).orElseThrow());
    } else {
        imageToSave = existingAvatar.get();
        System.out.println(imageToSave.getUser() + " Это владелец аватарки");
        imageToSave.setBytes(file.getBytes());
    }

    UserAvatar savedImage = avatarRepository.save(imageToSave);

    return savedImage.getBytes();
}


//    @Transactional
    public byte[] getAvatar(Integer id) {
        log.info("Was invoked method to get avatar from user with id {}", id);
        Optional<UserAvatar> userAvatar = avatarRepository.findById(id);
        if (isEmpty(userAvatar)) {
            throw new IllegalArgumentException("Avatar not found");
        }
        return userAvatar.get().getBytes();
    }

//    public String saveAds( MultipartFile image) {
//        AdsImage entity = new AdsImage();
//        try {
//            // код, который кладет картинку в entity
//            byte[] bytes = image.getBytes();
//            entity.setAdsImage(bytes);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        entity.setId(UUID.randomUUID().toString());
//        // код сохранения картинки в БД
//        AdsEntity savedEntity = repository.saveAndFlush(entity);
//        return savedEntity.getId();
}
