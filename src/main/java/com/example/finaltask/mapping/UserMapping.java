package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.UserDTO;
import com.example.finaltask.model.entity.User;

public class UserMapping {
    private final User user;
    private final UserDTO userDTO;

    public UserMapping(User user, UserDTO userDTO) {
        this.user = user;
        this.userDTO = userDTO;
    }

    //из dto в entity
    public User mapToUserEntity(UserDTO dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        //image
        //role
        return entity;
    }

    //из entity в dto
    public UserDTO mapToUserDto(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        //image
        //role
        return dto;

    }
    /*DTO    public class UserDTO {
        private Integer id;        private String email;
        private String firstName;        private String lastName;
        private String phone;        private String image;
    }*/
    /* entity    private Long id;
    private String email;    private String firstName;
    private String lastName;    private String phone;
    private Role role;*/
}
