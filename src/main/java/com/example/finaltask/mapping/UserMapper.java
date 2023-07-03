package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.RegisterReq;
import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "image", expression = "java(getAvatar(entity))")
    UserDTO toDto(User entity);

    @Mapping(source = "username",target = "email")
    @Mapping(source = "password",target = "password")
    User toEntity(RegisterReq registerReq);


    RegisterReq toDto2 (User user);

    default String getAvatar(User user) {
        if (user.getAvatar() == null) {
            return null;
        }
        return "/users/" + user.getId() + "/getImage";
    }


}
