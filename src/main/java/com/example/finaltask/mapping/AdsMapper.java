package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.dto.CreateAdsDTO;
import com.example.finaltask.model.entity.Ads;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    Ads toEntity1(AdsDTO dto);

    AdsDTO toDto1(Ads entity);
    Ads toEntity1(CreateAdsDTO createAdsDTO);

    CreateAdsDTO toDto(Ads ads);

    AdsDTO toDTO (CreateAdsDTO createAdsDTO);
}
