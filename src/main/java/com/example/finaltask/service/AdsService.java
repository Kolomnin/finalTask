package com.example.finaltask.service;

import com.example.finaltask.model.dto.AdsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface AdsService {

    Iterable<AdsDTO> getAllADS();

    AdsDTO addAd(AdsDTO adsDTO, MultipartFile image) throws IOException;

    Optional<AdsDTO> getADS(Integer id);

    boolean removeAd(Integer id);

    AdsDTO updateADS(AdsDTO adsDto, Integer id);

    AdsDTO getMe();

    byte[] updateImage(Integer id, MultipartFile image) throws IOException;

}