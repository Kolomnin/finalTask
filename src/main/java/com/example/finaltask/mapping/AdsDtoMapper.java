package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.CreateAdsDTO;
import com.example.finaltask.model.entity.Ads;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdsDtoMapper {
    @Mapping(target = "description", source = "description")
    Ads toEntity(CreateAdsDTO createAdsDTO);

//    @Mapping(target = "pk",source = "id")
    CreateAdsDTO toDto(Ads ads);
}
