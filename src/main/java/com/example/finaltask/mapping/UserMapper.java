package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO dto);

    UserDTO toDto(User entity);
}
