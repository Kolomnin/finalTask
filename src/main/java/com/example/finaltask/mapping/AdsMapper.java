package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.entity.Ads;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    Ads toEntity(AdsDTO dto);

    AdsDTO toDto(Ads entity);
}
