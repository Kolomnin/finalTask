package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.RegisterReq;
import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);
    @Mapping(source = "login",target = "email")
    @Mapping(target = "image", expression = "java(getAvatar(user))")
    UserDTO toDto(User user);

    @Mapping(source = "username",target = "login")
    @Mapping(source = "password",target = "password")
    User toEntity(RegisterReq registerReq);

    default String getAvatar(User user) {
        if (user.getAvatar() == null) {
            return null;
        }
        return "/users/" + user.getId() + "/getImage";
    }


    RegisterReq toDto2 (User user);


}
