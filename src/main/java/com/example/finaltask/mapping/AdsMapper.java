package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.dto.FullAdsDTO;
import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    Ads toEntity(AdsDTO dto);
//    @Mapping(target = "pk",source = "id")
//    @Mapping(target ="author",source = "authorId",qualifiedByName = "userToLong")
//    AdsDTO toDto(Ads entity);
@Mapping(source = "id", target = "pk")
@Mapping(target = "image", expression = "java(getImage(ads))")
@Mapping(target = "author", expression = "java(ads.getAuthorId().getId())")
AdsDTO toDto(Ads ads);

    default String getImage(Ads ads) {
        if (ads.getAdsImage() == null) {
            return null;
        }
        return "/ads/" + ads.getId() + "/getAdsImage";
    }

    @Mapping(target = "authorFirstName", source = "authorId.firstName")
    @Mapping(target = "authorLastName", source = "authorId.lastName")
    @Mapping(target = "email", source = "authorId.login")
    @Mapping(target = "image", expression="java(getImage(ads))")
    @Mapping(target = "phone", source = "authorId.phone")
    @Mapping(target = "pk", source = "id")
    FullAdsDTO adsToAdsDtoFull(Ads ads);


    @Named("userToLong")
    static Integer userToLong(User user) {
        return user != null ? user.getId() : null;
    }
}

