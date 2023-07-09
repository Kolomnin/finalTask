package com.example.finaltask.repository;

import com.example.finaltask.model.entity.AdsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsImageRepository extends JpaRepository<AdsImage, Integer> {

    AdsImage findImageByAds_Id(Integer id);
    AdsImage findAdsImageById(Integer id);



}