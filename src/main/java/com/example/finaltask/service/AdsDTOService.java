package com.example.finaltask.service;

import com.example.finaltask.mapping.AdsMapper;
import com.example.finaltask.mapping.ImageMapper;
import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.dto.CreateAdsDTO;
import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.model.entity.ImageAds;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class AdsDTOService {
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

//    private final UserDetailsManager manager;

    private final ImageMapper imageMapper;

    private final ImageDTOService imageDTOService;


    private final AdsMapper adsMapper;




    public AdsDTOService(AdsRepository adsRepository, UserRepository userRepository,
                         ImageMapper imageMapper, ImageDTOService imageDTOService, AdsMapper adsMapper) {
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
        this.imageMapper = imageMapper;
        this.imageDTOService = imageDTOService;

        this.adsMapper = adsMapper;

    }

    public AdsDTO addAds1(AdsDTO properties) {
        Ads ads = adsMapper.toEntity1(properties);
        AdsDTO adsDTO = adsMapper.toDto1(ads);
        ads.setAuthorId(userRepository.findById(1L).orElseThrow(null));//В след уроках покажут как получить
                                                        // пользователя который авторизован,пока юзер установлен
        adsRepository.save(ads);
        return adsDTO;
    }
    public Ads addAds2(CreateAdsDTO properties, MultipartFile file) throws IOException {
        AdsDTO adsDTO = adsMapper.toDTO(properties);
        System.out.println(adsDTO);

        Ads ads = adsMapper.toEntity1(adsDTO);
        ads.setAuthorId(userRepository.findById(1L).orElseThrow(null));


        ImageAds imageAds = imageMapper.toEntity(file);
        imageAds = imageDTOService.saveImageAds(imageAds);

//        CreateAdsDTO adsDTO1 = adsDtoMapper.toDto(ads);


        //В след уроках покажут как получить
        // пользователя который авторизован,пока юзер установлен


        return adsRepository.save(ads);
    }

    public Optional<Ads> getAdsById(Long id) {
        return adsRepository.findById(id);
    }

    public void deleteAdsById(Long id) {
        adsRepository.deleteById(id);
    }
    public Ads editAds(Ads ads ) {
        return adsRepository.save(ads);
    }

}
