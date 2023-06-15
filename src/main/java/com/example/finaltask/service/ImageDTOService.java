package com.example.finaltask.service;

import com.example.finaltask.model.entity.Image;
import com.example.finaltask.model.entity.ImageAds;
import com.example.finaltask.repository.ImageAdsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageDTOService {
    private final ImageAdsRepository imageAdsRepository;

    public ImageAds getImage(Long id) {
        return imageAdsRepository.getById(id);
    }

    public ImageAds saveImageAds(ImageAds imageAds) {
        return imageAdsRepository.save(imageAds);
    }
}
