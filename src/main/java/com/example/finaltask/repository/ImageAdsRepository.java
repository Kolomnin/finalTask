package com.example.finaltask.repository;

import com.example.finaltask.model.entity.ImageAds;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageAdsRepository extends JpaRepository<ImageAds,Long> {

    ImageAds getById (Long id);
}
