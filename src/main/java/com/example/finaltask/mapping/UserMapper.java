package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "id", target = "id")
    @Mapping(target = "email", source = "firstName")
    @Mapping(target = "image", expression = "java(user.getAvatar() != null ? user.getAvatar().getFilePath() : null)")
    UserDTO userToUserDto(User user);


    @Mapping(target = "role", ignore = true)
    @Mapping(target = "login", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    User userDtoToUser(UserDTO userDto);

}