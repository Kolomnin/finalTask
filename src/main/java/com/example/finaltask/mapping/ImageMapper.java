package com.example.finaltask.mapping;

import com.example.finaltask.model.entity.UserAvatar;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    UserAvatar toEntity (MultipartFile file) throws IOException;

}
