package com.example.finaltask.mapping;

import com.example.finaltask.model.dto.CommentDTO;
import com.example.finaltask.model.dto.CreateCommentDTO;
import com.example.finaltask.model.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(CommentDTO dto);

    CommentDTO toDto(Comment entity);

    CommentDTO toEntityCCD(CreateCommentDTO createCommentDTO);

    Comment  toEntity (CreateCommentDTO createCommentDTO);

    CreateCommentDTO toDto1 (Comment entity);
}
