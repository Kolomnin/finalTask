package com.example.finaltask.service;

import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdsDTOService {
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    public AdsDTOService(AdsRepository adsRepository, UserRepository userRepository) {
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
    }

    public Ads addAds(Ads ads) {

        ads.setAuthorId(userRepository.findById(1L));
        return adsRepository.save(ads);
    }
}
