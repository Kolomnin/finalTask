package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.dto.CreateAdsDTO;
import com.example.finaltask.model.dto.FullAdsDTO;
import com.example.finaltask.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FullAdsMapper {
    @Mapping(source = "userDTO.firstName", target = "authorFirstName")
    @Mapping(source = "userDTO.lastName", target = "authorLastName")
//    @Mapping(source = "adsDTO.description", target = "description")
    @Mapping(source = "userDTO.email", target = "email")
    @Mapping(source = "adsDTO.image", target = "image")
    @Mapping(source = "userDTO.phone", target = "phone")
    @Mapping(source = "adsDTO.pk", target = "pk")
    @Mapping(source = "adsDTO.price", target = "price")
    @Mapping(source = "adsDTO.title", target = "title")
    FullAdsDTO mergeAdsAndUserAndAds(UserDTO userDTO, AdsDTO adsDTO);
}

