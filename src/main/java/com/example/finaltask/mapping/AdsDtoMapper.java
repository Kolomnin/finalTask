package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.CreateAdsDTO;
import com.example.finaltask.model.entity.Ads;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdsDtoMapper {
    CreateAdsDTO toDto(Ads ads);
}
