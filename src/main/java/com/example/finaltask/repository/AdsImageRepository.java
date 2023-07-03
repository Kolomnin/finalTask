package com.example.finaltask.repository;

import com.example.finaltask.model.entity.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.finaltask.model.entity.AdsImage;

public interface AdsImageRepository extends JpaRepository<AdsImage, Integer> {

    AdsImage findImageByAds_Id(Integer id);
    AdsImage findAdsImageById(Integer id);



}