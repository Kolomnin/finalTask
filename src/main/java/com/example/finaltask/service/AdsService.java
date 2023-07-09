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
import lombok.RequiredArgsConstructor;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
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
        List<AdsDTO> adsDTO = new ArrayList<>();
        for (Ads ads : adsList) {
            adsDTO.add(adsMapper.toDto(ads));
        }
        return adsDTO;
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
        List<AdsDTO> adsDTO = new ArrayList<>();
        for (Ads ads : adsList) {
            adsDTO.add(adsMapper.toDto(ads));
        }
        return adsDTO;
    }

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
    /**
     * Получает объект FullAdsDTO, содержащий сведения об объявлении вместе с деталями.
     * пользователя, разместившего объявление.
     *
     * @param id идентификатор объявления для получения
     * @param authentication объект аутентификации, представляющий пользователя, вошедшего в систему в данный момент.
     * @return объект FullAdsDTO, содержащий объявление и сведения о пользователе.
     * @throws NoSuchElementException, если объявление или пользователь не могут быть найдены
     */
    public FullAdsDTO getFullAdsDTO(Integer id, Authentication authentication) {
        UserDTO userDTO = userMapper.toDto(userRepository.findByEmail(authentication.getName()).orElseThrow());
        AdsDTO adsDTO = adsMapper.toDto(adsRepository.findById(id).orElseThrow());
        System.out.println(adsDTO);
        logger.info(adsDTO.toString());
        FullAdsDTO fullAdsDTO = fullAdsMapper.mergeAdsAndUserAndAds(userDTO, adsDTO);
        logger.info(fullAdsDTO.toString());
        fullAdsDTO.setDescription(adsRepository.findByAuthorIdEmailAndId(authentication.getName(), id).getDescription());
        logger.info(fullAdsDTO.toString());

        return fullAdsDTO;
    }

    /**
     * Получает полную информацию об объекте Ads на основе его идентификатора.
     *
     * @param id Идентификатор объекта Ads, который требуется получить.
     * @return FullAdsDTO, содержащий полную информацию об объекте Ads.
     * @throws IllegalArgumentException, если сущность Ads с заданным идентификатором не найдена.
     */
    public FullAdsDTO getFullAds(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Ads not found"));
        log.info("Get ads: " + ads);
        return adsMapper.adsToAdsDtoFull(ads);
    }

    /**
     * Обновляет изображение с заданным идентификатором.
     *
     * @param id Идентификатор изображения, которое необходимо обновить.
     * @param image Файл изображения, который необходимо обновить.
     * @return Обновленное изображение в виде массива байтов.
     * @throws IOException Если есть ошибка при чтении файла изображения.
     * @throws IllegalArgumentException Если файл изображения пуст.
     */
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
