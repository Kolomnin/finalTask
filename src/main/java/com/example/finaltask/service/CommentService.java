package com.example.finaltask.service;

import com.example.finaltask.mapping.CommentMapper;
import com.example.finaltask.model.dto.CommentDTO;
import com.example.finaltask.model.dto.ResponseWrapperComment;
import com.example.finaltask.model.entity.Comment;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.CommentRepository;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;


    private final UserRepository userRepository;

    private final AdsRepository adsRepository;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper,
                           UserRepository userRepository, AdsRepository adsRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;

        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
    }


    public CommentDTO addComment(Integer id, CommentDTO commentDto, Authentication authentication) {
        if (!adsRepository.existsById(id)) {
            throw new IllegalArgumentException("Ad not found");
        }
        Comment newComment = commentMapper.toEntity(commentDto);
        newComment.setAds(adsRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Ad not found")));
        newComment.setAuthorId(userRepository.findByEmail(authentication.getName()).orElseThrow());
        commentRepository.save(newComment);
        return commentMapper.toDto(newComment);
    }

    public ResponseWrapperComment getAllCommentsByAdsId(Integer id) {
        List<Comment> comments = commentRepository.findAllByAds_Id((id));
        ResponseWrapperComment responseWrapperComment = new ResponseWrapperComment();
        responseWrapperComment.setCount(comments.size());
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            commentDTOS.add(commentMapper.toDto(comment));
        }
        responseWrapperComment.setResults(commentDTOS);
        return responseWrapperComment;
    }

    public void deleteCommentById(Integer id) {
        commentRepository.deleteById(id);
    }
    @Transactional
    public void deleteAllCommentsAds(Integer adsId) {
        log.info("deleteAll comment ads");
        commentRepository.deleteByAdsId(adsId);
    }
    public CommentDTO editComment(CommentDTO commentDTO) {
        Comment comment = commentMapper.toEntity(commentDTO);
        commentRepository.save(comment);
        CommentDTO commentDTO1 = commentMapper.toDto(commentRepository.findById(comment.getId()).orElseThrow());
        return commentDTO1;
    }


}
