package com.example.finaltask.service;

import com.example.finaltask.mapping.CommentMapper;
import com.example.finaltask.model.dto.CommentDTO;
import com.example.finaltask.model.dto.CreateCommentDTO;
import com.example.finaltask.model.dto.ResponseWrapperComment;
import com.example.finaltask.model.entity.Ads;
import com.example.finaltask.model.entity.Comment;
import com.example.finaltask.repository.CommentRepository;
import com.example.finaltask.repository.UserRepository;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final AdsService adsService;

    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper,
                          AdsService adsService, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.adsService = adsService;
        this.userRepository = userRepository;
    }

    public CommentDTO addComment(CreateCommentDTO createCommentDTO, Integer id, @NonNull Authentication authentication) {
        Integer userId = userRepository.findByLogin(authentication.getName()).get().getId();
        System.out.println("id комментарий"+userId);
        Comment comment = commentMapper.toEntity(createCommentDTO);
        Ads ads = adsService.getAdsById(id).orElseThrow();
        comment.setAds(ads);
        comment.setCreatedAt(11111111l);
        comment.setAuthorId(userRepository.findByLogin(authentication.getName()).orElseThrow());
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    public ResponseWrapperComment getAllCommentsByAdsId(Long id) {
        List<Comment> comments = commentRepository.findAllByAds_Id(id);
        ResponseWrapperComment responseWrapperComment = new ResponseWrapperComment();
        responseWrapperComment.setCount(comments.size());
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            commentDTOS.add(commentMapper.toDto(comment));
        }
        responseWrapperComment.setResults(commentDTOS);
        return responseWrapperComment;
    }

    public void getCommentById(Long id){

    }
    public void deleteCommentById(Integer id) {
        commentRepository.deleteById(id);
    }

    public CommentDTO editComment(CommentDTO commentDTO) {
        Comment comment = commentMapper.toEntity(commentDTO);
        commentRepository.save(comment);
        CommentDTO commentDTO1 = commentMapper.toDto(commentRepository.findById(comment.getId()));
        return commentDTO1;
    }


}
