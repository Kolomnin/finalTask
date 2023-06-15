package com.example.finaltask.mapping;

import com.example.finaltask.model.entity.Image;
import com.example.finaltask.model.entity.ImageAds;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toDto(ImageAds imageAds) throws IOException;

    ImageAds toEntity(MultipartFile file) throws IOException;


}
