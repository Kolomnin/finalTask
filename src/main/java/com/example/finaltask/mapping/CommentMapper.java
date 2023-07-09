package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.CommentDTO;
import com.example.finaltask.model.dto.CreateCommentDTO;
import com.example.finaltask.model.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toEntity(CreateCommentDTO createCommentDTO);
    CommentDTO toEntityCCD(CreateCommentDTO createCommentDTO);
    CreateCommentDTO toDto1 (Comment entity);

    @Mapping(source = "author.id", target = "author")
    @Mapping(target = "authorImage", expression = "java(image(comment))")
    @Mapping(source = "author.firstName", target = "authorFirstName")
    @Mapping(source = "id", target = "pk")
    CommentDTO toCommentDTO(Comment comment);

    default String image(Comment comment) {
        int id = comment.getAuthor().getId();
        return "/users/" + id + "/getImage";
    }
}