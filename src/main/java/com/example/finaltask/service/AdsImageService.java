package com.example.finaltask.service;

import com.example.finaltask.mapping.AdsMapper;
import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.model.entity.AdsImage;
import com.example.finaltask.repository.AdsImageRepository;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.AvatarRepository;
import com.example.finaltask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdsImageService {
    private final AdsImageRepository adsImageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final AdsMapper adsMapper;
    private final AvatarRepository avatarRepository;

    /**
     * Сохраняет файл изображения для данной рекламы.
     *
     * @param id идентификатор объявления
     * @param file файл изображения для сохранения
     * @вернуть сохраненное изображение в виде массива байтов
     * @throws IOException, если при чтении файла возникает ошибка ввода-вывода
     * @throws IllegalArgumentException, если файл пуст
     * @throws UsernameNotFoundException, если реклама не найдена
     */
    public byte[] saveImage(Integer id, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Ads not found"));
        AdsImage imageToSave = new AdsImage();
        imageToSave.setAds(ads);
        imageToSave.setImage(file.getBytes());

        imageToSave.setUser(userRepository.findById(ads.getAuthorId().getId()).get());
        System.out.println(ads);
        AdsImage imageToSave2 = adsImageRepository.saveAndFlush(imageToSave);

        return imageToSave.getImage();
    }

    /**
     * Обновляет изображение для указанного объявления с заданным идентификатором.
     *
     * @param id Идентификатор объявления, для которого необходимо обновить изображение.
     * @param file Новый загружаемый файл изображения.
     * @return Обновленное изображение в виде массива байтов.
     * @throws IOException Если при чтении файла возникает ошибка ввода-вывода.
     * @throws IllegalArgumentException Если файл пуст или изображение с заданным идентификатором не найдено.
     */
    public byte[] updateImage(Integer id, MultipartFile file) throws IOException {
        log.info("Was invoked method to upload photo to ads with id {}", id);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        AdsImage imageToSave = adsImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Image not found"));

        imageToSave.setImage(file.getBytes());


        AdsImage savedImage = adsImageRepository.save(imageToSave);

        return savedImage.getImage();
    }

    /**
     * Извлекает данные изображения для данного объявления по его идентификатору.
     *
     * @param id Идентификатор объявления.
     * @return Данные изображения в виде массива байтов.
     * @throws IllegalArgumentException Если изображение не найдено.
     */
    @Transactional
    public byte[] getImage(Integer id) {
        log.info("Was invoked method to get image from ads with id {}", id);
        AdsImage image = adsImageRepository.findAdsImageById(id);
        log.info("картинка появляется");
        if (isEmpty(image)) {
            throw new IllegalArgumentException("Image not found");
        }
        return image.getImage();
    }

    /**
     * Удаляет объявление с указанным идентификатором.
     *
     * @param id идентификатор удаляемого графического объявления
     */
    public void deleteImageAds(Integer id) {
        log.info("Удаление картинки под номером" + id);
        adsImageRepository.deleteById(id);
    }

}