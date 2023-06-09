package com.example.finaltask.service;

import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.UserRepository;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AdsDTOService {
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    private final UserDetailsManager manager;

    public AdsDTOService(AdsRepository adsRepository, UserRepository userRepository, UserDetailsManager manager) {
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
        this.manager = manager;
    }

    public Ads addAds(Ads ads) {

//        manager.userExists()
//        userRepository.findByLogin();
        ads.setAuthorId(userRepository.findById(1L));
        return adsRepository.save(ads);
    }
}
