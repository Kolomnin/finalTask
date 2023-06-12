package com.example.finaltask.service;

import com.example.finaltask.mapping.CommentMapper;
import com.example.finaltask.model.dto.CommentDTO;
import com.example.finaltask.model.dto.CreateCommentDTO;
import com.example.finaltask.model.entity.Comment;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentDTOService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    public CommentDTOService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }
    //Пока много лищнего, так как до комментов еще не дошли,только маппер и сохранение в бд
    public CommentDTO addComment (CreateCommentDTO createCommentDTO){
        Comment comment = commentMapper.toEntity(createCommentDTO);
        CreateCommentDTO createCommentDTO1 = commentMapper.toDto1(comment);
        CommentDTO commentDTO = commentMapper.toDto(comment);
        Comment comment1 = commentMapper.toEntity(commentDTO);
        commentRepository.save(comment);
        return commentDTO;
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public void deleteCommentById(Integer id) {
        commentRepository.deleteById(id);
    }
    public Comment editComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment editCommentDto(CommentDTO commentDto) {
       Comment comment = commentMapper.toEntity(commentDto);
        return commentRepository.save(comment);
    }


}
