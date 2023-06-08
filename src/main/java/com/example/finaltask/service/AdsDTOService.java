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

    public Ads addAds(AdsDTO adsDTO) {
        Ads ads = new Ads();
        ads.setPrice(adsDTO.getPrice());
        ads.setTitle(adsDTO.getTitle());
        ads.setAuthorId(userRepository.findById(1L));




//        user.setId(userdto.ge());
//        user.setEmail(userdto.getEmail());
        return adsRepository.save(ads);
    }
    public Ads addAds(Ads ads) {

        ads.setAuthorId(userRepository.findById(1L));
        return adsRepository.save(ads);
    }
}
