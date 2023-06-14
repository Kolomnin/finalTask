package com.example.finaltask.service;

import com.example.finaltask.model.dto.AdsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface AdsService {

    public Iterable<AdsDTO> getAllADS();

    public AdsDTO addAd(AdsDTO adsDto, MultipartFile image) throws IOException;

    public Optional<AdsDTO> getAds(Long id);

    public boolean removeAd(Long id);

    public AdsDTO updateAds(AdsDTO adsDto, Long id);

    public Collection<AdsDTO> getMe(String email);

    public byte[] updateImage(Long id, MultipartFile image) throws IOException;

}