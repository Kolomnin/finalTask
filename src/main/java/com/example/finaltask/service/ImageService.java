package com.example.finaltask.service;

import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.model.entity.Image;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.ImageRepository;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    public ImageService(ImageRepository imageRepository, AdsRepository adsRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
    }

    public byte[] saveImage(Long id, MultipartFile file) throws IOException {
        log.info("Was invoked method to upload photo to ads with id {}", id);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        Ads ads = adsRepository.findById(id);
        Image imageToSave = new Image();
        imageToSave.setId(id);
        imageToSave.setAds(ads);
        imageToSave.setPreview(file.getBytes());
        imageRepository.save(imageToSave);
        return imageToSave.getPreview();
    }



}