package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO toDto(User entity);
}
