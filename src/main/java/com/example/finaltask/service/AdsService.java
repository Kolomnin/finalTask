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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdsService {
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    private final AdsImageService adsImageService;
    private final AdsMapper adsMapper;
    private final AdsDtoMapper adsDtoMapper;

    private final FullAdsMapper fullAdsMapper;

    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(AdsService.class);

    private final CommentService commentService;


    public AdsService(AdsRepository adsRepository, UserRepository userRepository, AdsImageService adsImageService, AdsMapper adsMapper, AdsDtoMapper adsDtoMapper, FullAdsMapper fullAdsMapper, UserMapper userMapper, CommentService commentService) {

        this.adsRepository = adsRepository;
        this.userRepository = userRepository;

        this.adsImageService = adsImageService;
        this.adsMapper = adsMapper;
        this.adsDtoMapper = adsDtoMapper;
        this.fullAdsMapper = fullAdsMapper;
        this.userMapper = userMapper;

        this.commentService = commentService;
    }

    /**
     * Добавляет новую рекламу.
     *
     * @param createAdsDTO DTO, содержащий сведения о создаваемой рекламе.
     * @param image Файл изображения для рекламы
     * @param authentication Объект аутентификации, представляющий текущего пользователя.
     * @return DTO, представляющий только что созданную рекламу
     * @throws IOException Если есть ошибка при сохранении файла изображения
     */
    public AdsDTO addAd(CreateAdsDTO createAdsDTO, MultipartFile image, Authentication authentication) throws IOException {
        Ads newAds = adsMapper.toEntity(createAdsDTO);
        newAds.setAuthorId(userRepository.findByEmail(authentication.getName()).orElseThrow());
        adsRepository.save(newAds);
        log.info("Save ads: " + newAds);
        if (image != null) {
            adsImageService.saveImage(newAds.getId(), image);
            log.info("Photo has been saved");
        } else {
            throw new IOException("Photo not found");
        }
        return adsMapper.toDto(newAds);
    }

    /**
     * Извлекает объект Ads по его идентификатору.
     *
     * @param id ID объекта Ads для получения
     * @return необязательный объект, содержащий объект Ads, или пустой необязательный объект, если объект Ads не найден
     */
    public Optional<Ads> getAdsById(Integer id) {
        return adsRepository.findById(id);
    }

    /**
     * Получает все объявления из базы данных.
     *
     * @return список объектов AdsDTO, представляющих все объявления.
     */
    public List<AdsDTO> getAllAds() {
        List<Ads> adsList = adsRepository.findAll();
        List<AdsDTO> adsDTOS = new ArrayList<>();
        for (Ads ads : adsList) {
            adsDTOS.add(adsMapper.toDto(ads));
        }
        return adsDTOS;
    }

    /**
     * Извлекает список объектов AdsDTO для аутентифицированного пользователя.
     *
     * @param authentication объект аутентификации, содержащий информацию о текущем пользователе.
     * @return список объектов AdsDTO для аутентифицированного пользователя
     * @throws IllegalArgumentException, если объект аутентификации имеет значение null или аутентифицированный пользователь не найден в userRepository
     */
    public List<AdsDTO> getAdsAuthUser(Authentication authentication) {
        List<Ads> adsList = adsRepository.findAllByAuthorId(
                userRepository.
                        findByEmail(authentication.getName()).orElseThrow());
        List<AdsDTO> adsDTOS = new ArrayList<>();
        for (Ads ads : adsList) {
            adsDTOS.add(adsMapper.toDto(ads));
        }
        return adsDTOS;
    }

    //    @Transactional
//    public void deleteAdsById(Integer id) {
////        adsRepository.deleteAdsById(id);
//        adsRepository.deleteById(id);
//    }
    /**
     * Удаляет объявление по его идентификатору вместе с соответствующими комментариями и изображением.
     *
     * @param adId идентификатор удаляемой рекламы
     * @throws IllegalArgumentException, если объявление с данным идентификатором не найдено
     */
    @Transactional
    public void deleteAds(Integer adId) {
        log.info("Request to delete ad by id");
        Ads ad = adsRepository.findById(adId).orElseThrow(()
                -> new IllegalArgumentException("Ad not found with id: " + adId));
        commentService.deleteAllCommentsAds(adId);
        adsImageService.deleteImageAds(ad.getImage().getId());
        adsRepository.delete(ad);
    }

    //    public Ads editAds(Ads ads ) {
//        return adsRepository.save(ads);
//    }
    /**
     * Обновляет объявление с указанным идентификатором, используя информацию, предоставленную в CreateAdsDTO.
     *
     * @param createAdsDTO Объект CreateAdsDTO, содержащий обновленную информацию об объявлении.
     * @param id Идентификатор объявления, которое необходимо обновить.
     * @return Обновленный объект AdsDTO.
     * @throws IllegalArgumentException Если цена в CreateAdsDTO меньше нуля.
     */
    public AdsDTO updateAds(CreateAdsDTO createAdsDTO, Integer id) {
        log.info("Request to update ad by id");
        if (createAdsDTO.getPrice() < 0) {
            throw new IllegalArgumentException("Prise less null");
        }
        Ads ads = adsRepository.findById(id).orElseThrow(null);
        ads.setDescription(createAdsDTO.getDescription());
        ads.setTitle(createAdsDTO.getTitle());
        ads.setPrice(createAdsDTO.getPrice());
        adsRepository.save(ads);
        return adsMapper.toDto(ads);
    }

    public FullAdsDTO getFullAdsDTO(Integer id, Authentication authentication) {
        UserDTO userDTO = userMapper.toDto(userRepository.findByEmail(authentication.getName()).orElseThrow());
//        AdsDTO adsDTO = adsMapper.toDto(adsRepository.findByAuthorId(userRepository.findByEmail(authentication.getName()).getId()));
        AdsDTO adsDTO = adsMapper.toDto(adsRepository.findById(id).orElseThrow());
        System.out.println(adsDTO);
        logger.info(adsDTO.toString());
        FullAdsDTO fullAdsDTO = fullAdsMapper.mergeAdsAndUserAndAds(userDTO, adsDTO);
        logger.info(fullAdsDTO.toString());
        fullAdsDTO.setDescription(adsRepository.findByAuthorIdEmailAndId(authentication.getName(), id).getDescription());
        logger.info(fullAdsDTO.toString());

        return fullAdsDTO;
    }

    public FullAdsDTO getFullAds(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Ads not found"));
        log.info("Get ads: " + ads);
        return adsMapper.adsToAdsDtoFull(ads);
    }

    public byte[] updateImage(Integer id, MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("Image now found");
        }
        log.info("Update image: " + id);
        adsImageService.updateImage(id, image);
        log.info("Photo have been saved");
        return image.getBytes();
    }

}
