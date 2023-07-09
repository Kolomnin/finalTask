package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.AdsDTO;
import com.example.finaltask.model.dto.CreateAdsDTO;
import com.example.finaltask.model.dto.FullAdsDTO;
import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.model.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AdsMapper {
//    @InheritInverseConfiguration
//    @Mapping(target = "image", ignore = true)
//    @Mapping(target = "authorId", ignore = true)
//    @Mapping(target = "description", ignore = true)
//    Ads toEntity(AdsDTO dto);
//    @Mapping(target = "pk",source = "id")
//    @Mapping(target ="author",source = "authorId",qualifiedByName = "userToLong")
//    AdsDTO toDto(Ads entity);
@Mapping(target = "pk", source = "id")
@Mapping(target = "image", expression = "java(getImage(ads))")
@Mapping(target = "author", source = "id")
AdsDTO toDto(Ads ads);



    default String getImage(Ads ads) {
        if (ads.getImage() == null) {
            return null;
        }
        return "/ads/" + ads.getId() + "/getImage";
    }

    @Mapping(target = "authorFirstName", source = "authorId.firstName")
    @Mapping(target = "authorLastName", source = "authorId.lastName")
    @Mapping(target = "email", source = "authorId.email")
    @Mapping(target = "image", expression="java(getImage(ads))")
    @Mapping(target = "phone", source = "authorId.phone")
    @Mapping(target = "pk", source = "id")
    FullAdsDTO adsToAdsDtoFull(Ads ads);


    @Mapping(target = "description", source = "description")

    Ads toEntity(CreateAdsDTO createAdsDTO);


    @Named("userToLong")
    static Integer userToLong(User user) {
        return user != null ? user.getId() : null;
    }
}

