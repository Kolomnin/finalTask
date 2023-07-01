package com.example.finaltask.service;

import com.example.finaltask.mapping.AdsDtoMapper;
import com.example.finaltask.mapping.AdsMapper;
import com.example.finaltask.mapping.FullAdsMapper;
import com.example.finaltask.mapping.UserMapper;
import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.dto.CreateAdsDTO;
import com.example.finaltask.model.dto.FullAdsDTO;
import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdsService {
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    private final UserDetailsManager manager;


    private final AdsMapper adsMapper;
    private final AdsDtoMapper adsDtoMapper;

    private final FullAdsMapper fullAdsMapper;

    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(AdsService.class);



    public AdsService(AdsRepository adsRepository, UserRepository userRepository, UserDetailsManager manager, AdsMapper adsMapper, AdsDtoMapper adsDtoMapper, FullAdsMapper fullAdsMapper, UserMapper userMapper) {
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
        this.manager = manager;
        this.adsMapper = adsMapper;
        this.adsDtoMapper = adsDtoMapper;
        this.fullAdsMapper = fullAdsMapper;
        this.userMapper = userMapper;
    }

//    public AdsDTO addAds1(AdsDTO properties) {
//        Ads ads = adsMapper.toEntity(properties);
//        AdsDTO adsDTO = adsMapper.toDto(ads);
//        ads.setAuthorId(userRepository.findById(1L));
//        adsRepository.save(ads);
//        return adsDTO;
//    }
    public Ads addAds2(CreateAdsDTO properties, Authentication authentication) {
        Ads ads = adsDtoMapper.toEntity(properties);
        System.out.println("Объявление создано");
        System.out.println(properties.getDescription());
        AdsDTO adsDTO = adsMapper.toDto(ads);
        System.out.println(adsDTO);
        ads.setAuthorId(userRepository.findByLogin(authentication.getName()).orElseThrow(null));
        adsRepository.save(ads);
        return ads;
    }

    public Optional<Ads> getAdsById(Integer id) {
        return adsRepository.findById(id);
    }

    public List<AdsDTO> getAllAds (){
        List<Ads> adsList = adsRepository.findAll();
        List<AdsDTO> adsDTOS = new ArrayList<>();
        for (Ads ads : adsList) {
            adsDTOS.add(adsMapper.toDto(ads));
        }
        return adsDTOS;
    }
    @Transactional
    public void deleteAdsById(Integer id) {
//        adsRepository.deleteAdsById(id);
        adsRepository.deleteById(id);
    }
    public Ads editAds(Ads ads ) {
        return adsRepository.save(ads);
    }

    public FullAdsDTO getFullAdsDTO(Integer id,Authentication authentication) {
        UserDTO userDTO = userMapper.toDto(userRepository.findByLogin(authentication.getName()).orElseThrow());
//        AdsDTO adsDTO = adsMapper.toDto(adsRepository.findByAuthorId(userRepository.findByLogin(authentication.getName()).getId()));
        AdsDTO adsDTO = adsMapper.toDto(adsRepository.findById(id).get());
        System.out.println(adsDTO);
        logger.info(adsDTO.toString());
        FullAdsDTO fullAdsDTO = fullAdsMapper.mergeAdsAndUserAndAds(userDTO,adsDTO);
        logger.info(fullAdsDTO.toString());
        fullAdsDTO.setDescription(adsRepository.findByAuthorIdLoginAndId(authentication.getName(),id).getDescription());
        logger.info(fullAdsDTO.toString());

        return fullAdsDTO;
    }

}
