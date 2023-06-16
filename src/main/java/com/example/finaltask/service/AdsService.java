package com.example.finaltask.service;

import com.example.finaltask.mapping.AdsDtoMapper;
import com.example.finaltask.mapping.AdsMapper;
import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.dto.CreateAdsDTO;
import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.UserRepository;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdsService {
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    private final UserDetailsManager manager;


    private final AdsMapper adsMapper;
    private final AdsDtoMapper adsDtoMapper;


    public AdsService(AdsRepository adsRepository, UserRepository userRepository, UserDetailsManager manager, AdsMapper adsMapper, AdsDtoMapper adsDtoMapper) {
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
        this.manager = manager;
        this.adsMapper = adsMapper;
        this.adsDtoMapper = adsDtoMapper;
    }

    public AdsDTO addAds1(AdsDTO properties) {
        Ads ads = adsMapper.toEntity(properties);
        AdsDTO adsDTO = adsMapper.toDto(ads);
        ads.setAuthorId(userRepository.findById(1L));//В след уроках покажут как получить
                                                        // пользователя который авторизован,пока юзер установлен
        adsRepository.save(ads);
        return adsDTO;
    }
    public AdsDTO addAds2(CreateAdsDTO properties) {
        Ads ads = adsDtoMapper.toEntity(properties);
        System.out.println("Объявление создано");
        System.out.println(properties.getDescription());
        AdsDTO adsDTO = adsMapper.toDto(ads);
        ads.setAuthorId(userRepository.findById(1L));//В след уроках покажут как получить
        // пользователя который авторизован,пока юзер установлен
        adsRepository.save(ads);
        return adsDTO;
    }

    public Ads getAdsById(Long id) {
        return adsRepository.findById(id);
    }

    public List<Ads> getAllAds (){
        return adsRepository.findAll();
    }

    public void deleteAdsById(Integer id) {
        adsRepository.deleteById(id);
    }
    public Ads editAds(Ads ads ) {
        return adsRepository.save(ads);
    }

}
