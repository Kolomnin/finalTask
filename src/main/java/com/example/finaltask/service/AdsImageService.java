package com.example.finaltask.service;

import com.example.finaltask.mapping.AdsMapper;
import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.dto.CreateAdsDTO;
import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.model.entity.AdsImage;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.model.entity.UserAvatar;
import com.example.finaltask.repository.AdsImageRepository;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.AvatarRepository;
import com.example.finaltask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

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

public byte[] saveImage(Integer id, MultipartFile file) throws IOException {
    if (file.isEmpty()) {
        throw new IllegalArgumentException("File is empty");
    }
    Ads ads = adsRepository.findById(id).orElseThrow((null));
    AdsImage imageToSave = new AdsImage();
//    imageToSave.setId(id);
    imageToSave.setAds(ads);
    imageToSave.setPreview(file.getBytes());
    imageToSave.setMediaType(file.getContentType());
    imageToSave.setFileSize(file.getSize());
    imageToSave.setFilePath(file.getOriginalFilename());
    imageToSave.setUser(userRepository.findById(ads.getAuthorId().getId()).get());
    System.out.println(ads);
    AdsImage imageToSave2 = adsImageRepository.saveAndFlush(imageToSave);

    return imageToSave.getPreview();
}

    public AdsDTO updateAds(CreateAdsDTO createAdsDTO, Integer id) {
        log.info("Request to update ad by id");
        if (createAdsDTO.getPrice() < 0) {
            throw new IllegalArgumentException("Prise less null");
        }
        Ads ads = adsRepository.findById(id).orElseThrow(null);
        ads.setDescription(createAdsDTO.getDescription());
        ads.setTitle(createAdsDTO.getTitle());
        ads.setPrice(createAdsDTO.getPrice());
        adsRepository.save(ads);
        return adsMapper.toDto(ads);
    }
    public byte[] updateImage(Integer id, MultipartFile file) throws IOException {
        log.info("Was invoked method to upload photo to ads with id {}", id);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        AdsImage imageToSave = adsImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Image not found"));

        imageToSave.setPreview(file.getBytes());
        imageToSave.setMediaType(file.getContentType());
        imageToSave.setFileSize(file.getSize());
        imageToSave.setFilePath(file.getOriginalFilename());

        AdsImage savedImage = adsImageRepository.save(imageToSave);

        return savedImage.getPreview();
    }

@Transactional
    public byte[] getImage(Integer id) { //for AdsMapper
        log.info("Was invoked method to get image from ads with id {}", id);
        AdsImage image = adsImageRepository.findAdsImageById(id);
        System.out.println("картинка появляется");
        if (isEmpty(image)) {
            throw new IllegalArgumentException("Image not found");
        }
        return image.getPreview();
    }


}