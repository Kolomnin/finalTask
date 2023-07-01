package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    Ads toEntity(AdsDTO dto);
    @Mapping(target = "pk",source = "id")
    @Mapping(target ="author",source = "authorId",qualifiedByName = "userToLong")
    AdsDTO toDto(Ads entity);


    @Named("userToLong")
    static Integer userToLong(User user) {
        return user != null ? user.getId() : null;
    }
}

