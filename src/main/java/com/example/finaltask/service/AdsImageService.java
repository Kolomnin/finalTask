package com.example.finaltask.service;

import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.model.entity.AdsImage;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.AdsImageRepository;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class AdsImageService {
    private final AdsImageRepository adsImageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    public AdsImageService(AdsImageRepository adsImageRepository, AdsRepository adsRepository, UserRepository userRepository) {
        this.adsImageRepository = adsImageRepository;
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
    }

    public byte[] saveImage(Ads ads, MultipartFile file, Authentication authentication) throws IOException {
        log.info("Was invoked method to upload photo to ads with id {}");
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        AdsImage adsImageToSave = new AdsImage();
        adsImageToSave.setUser(userRepository.findByLogin(authentication.getName()));
        adsImageToSave.setAds(ads);
        adsImageToSave.setPreview(file.getBytes());

        adsImageRepository.save(adsImageToSave);
        return adsImageToSave.getPreview();
    }



}