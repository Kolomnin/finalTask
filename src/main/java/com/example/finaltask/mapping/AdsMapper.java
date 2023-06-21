package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.entity.Ads;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    Ads toEntity(AdsDTO dto);
    @Mapping(target = "pk",source = "id")
    @Mapping(target ="author",source = "id")
    AdsDTO toDto(Ads entity);
}
