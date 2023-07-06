package com.example.finaltask.service;

import com.example.finaltask.mapping.ImageMapper;
import com.example.finaltask.model.entity.UserAvatar;
import com.example.finaltask.repository.AvatarRepository;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
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

    /**
     * Сохраняет изображение аватара пользователя.
     *
     * @param file           составной файл, содержащий сохраняемое изображение.
     * @param authentication объект аутентификации, содержащий информацию о текущем вошедшем в систему пользователе
     * @return сохраненный объект UserAvatar
     * @throws IOException, если при обработке файла возникает ошибка
     */
    public UserAvatar saveImage(MultipartFile file, Authentication authentication) throws IOException {
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
    /**
     * Сохраняет изображение аватара для пользователя.
     *
     * @param email адрес электронной почты пользователя
     * @param file файл изображения аватара, который нужно сохранить
     * @return массив байтов, представляющий сохраненное изображение
     * @throws IOException, если есть ошибка при чтении или сохранении файла изображения
     * @throws IllegalArgumentException, если файл пуст
     */
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
    /**
     * Извлекает данные изображения аватара для пользователя с заданным идентификатором.
     *
     * @param id Идентификатор пользователя.
     * @return Массив байтов, содержащий данные изображения аватара.
     * @throws IllegalArgumentException Если аватар для пользователя не найден.
     */
    public byte[] getAvatar(Integer id) {
        log.info("Was invoked method to get avatar from user with id {}", id);
        Optional<UserAvatar> userAvatar = avatarRepository.findById(id);
        if (isEmpty(userAvatar)) {
            throw new IllegalArgumentException("Avatar not found");
        }
        return userAvatar.get().getBytes();
    }


}
