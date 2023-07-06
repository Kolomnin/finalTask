package com.example.finaltask.service;

import com.example.finaltask.mapping.CommentMapper;
import com.example.finaltask.mapping.UserMapper;
import com.example.finaltask.model.dto.CommentDTO;
import com.example.finaltask.model.dto.CreateCommentDTO;
import com.example.finaltask.model.dto.ResponseWrapperComment;
import com.example.finaltask.model.entity.Comment;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.CommentRepository;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;


    private final UserRepository userRepository;

    private final AdsRepository adsRepository;

    private final UserMapper userMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper,
                          UserRepository userRepository, AdsRepository adsRepository, UserMapper userMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;

        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
        this.userMapper = userMapper;
    }


    public CommentDTO addComment(Integer id, CreateCommentDTO commentDto, Authentication authentication) {
        if (!adsRepository.existsById(id)) {
            throw new IllegalArgumentException("Ad not found");
        }
        Comment newComment = commentMapper.toEntity(commentDto);
        newComment.setAds(adsRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Ad not found")));
        newComment.setAuthor(userRepository.findByEmail(authentication.getName()).orElseThrow());
        newComment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(newComment);
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthor(newComment.getAuthor().getId());
        commentDTO.setAuthorImage(userMapper.getAvatar(userRepository.findByEmail(authentication.getName()).orElseThrow()));
        commentDTO.setAuthorFirstName(userRepository.findByEmail(authentication.getName()).orElseThrow().getFirstName());
        commentDTO.setPk(newComment.getId());
        System.out.println("ади коммента "+ newComment.getId());
        commentDTO.setText(newComment.getText());
        commentDTO.setCreatedAt(newComment.getCreatedAt());
        System.out.println("Сущность коммента "+commentDto);

        return commentDTO;
    }

    public ResponseWrapperComment getAllCommentsByAdsId(Integer id,Authentication authentication) {
        List<Comment> comments = commentRepository.findAllByAds_Id((id));
        ResponseWrapperComment responseWrapperComment = new ResponseWrapperComment();
        responseWrapperComment.setCount(comments.size());
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setAuthor(comment.getAuthor().getId());
            commentDTO.setText(comment.getText());
            commentDTO.setAuthorImage(userMapper.getAvatar(userRepository.findByEmail(authentication.getName()).orElseThrow()));
            commentDTO.setAuthorFirstName(userRepository.findByEmail(authentication.getName()).orElseThrow().getFirstName());
            commentDTO.setPk(comment.getId());
            System.out.println(commentDTO.getPk()+"айди");
            commentDTO.setCreatedAt(comment.getCreatedAt());
            commentDTOS.add(commentDTO);
            System.out.println("Сущность коммента "+commentDTO.getPk());
        }
        responseWrapperComment.setResults(commentDTOS);
        return responseWrapperComment;
    }
    @Transactional
    public void deleteCommentById(Integer adsId,Integer commentId) {
//        commentRepository.deleteByIdAndAds_Id(adsId).orElseThrow(null);//написать замену null
        log.info("delete comment ads ");
        commentRepository.deleteByIdAndAds_Id(commentId,adsId);

    }

    @Transactional
    public void deleteAllCommentsAds(Integer adsId) {
        log.info("deleteAll comment ads");
        commentRepository.deleteByAdsId(adsId);
    }

    public CommentDTO editComment(Integer adsId, Integer commentId, CommentDTO commentDTO) {
        log.info("updateComment method");
        Comment updatedComment = commentRepository.findByIdAndAds_Id(commentId,adsId ).orElseThrow();
        updatedComment.setText(commentDTO.getText());
        commentRepository.save(updatedComment);

        return commentMapper.toCommentDTO(updatedComment);
    }

    public User isOwnerCommentById(Integer id){
        return commentRepository.findById(id).get().getAuthor();
    }

}
