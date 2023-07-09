package com.example.finaltask.service;

import com.example.finaltask.mapping.CommentMapper;
import com.example.finaltask.mapping.UserMapper;
import com.example.finaltask.model.dto.CommentDTO;
import com.example.finaltask.model.dto.CreateCommentDTO;
import com.example.finaltask.model.dto.ResponseWrapperComment;
import com.example.finaltask.model.entity.Comment;
import com.example.finaltask.repository.AdsRepository;
import com.example.finaltask.repository.CommentRepository;
import com.example.finaltask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Добавляет новый комментарий к объявлению с указанным ID.
     *
     * @param id Идентификатор объявления.
     * @param commentDto Добавляемые данные комментария.
     * @param authentication Объект аутентификации, содержащий информацию об аутентифицированном пользователе.
     * @return Представление добавленного комментария DTO (объект передачи данных).
     * @throws IllegalArgumentException Если объявление с указанным идентификатором не найдено.
     */
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
        System.out.println("ади коммента " + newComment.getId());
        commentDTO.setText(newComment.getText());
        commentDTO.setCreatedAt(newComment.getCreatedAt());
        System.out.println("Сущность коммента " + commentDto);

        return commentDTO;
    }

    /**
     * Извлекает все комментарии по идентификатору объявления.
     *
     * @param id идентификатор объявления
     * @param authentication детали аутентификации пользователя
     * @return ResponseWrapperComment, содержащий количество комментариев и список объектов CommentDTO
     */
    public ResponseWrapperComment getAllCommentsByAdsId(Integer id, Authentication authentication) {
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
            System.out.println(commentDTO.getPk() + "айди");
            commentDTO.setCreatedAt(comment.getCreatedAt());
            commentDTOS.add(commentDTO);
            System.out.println("Сущность коммента " + commentDTO.getPk());
        }
        responseWrapperComment.setResults(commentDTOS);
        return responseWrapperComment;
    }

    /**
     * Удаляет комментарий по его идентификатору внутри определенного объявления.
     *
     * @param adsId идентификатор объявления
     * @param commentId идентификатор удаляемого комментария
     */
    @Transactional
    public void deleteCommentById(Integer adsId, Integer commentId) {
//        commentRepository.deleteByIdAndAds_Id(adsId).orElseThrow(null);//написать замену null
        log.info("delete comment ads ");
        commentRepository.deleteByIdAndAds_Id(commentId, adsId);

    }

    /**
     * Удаляет все комментарии, относящиеся к конкретному объявлению.
     *
     * @param adsId идентификатор объявления, комментарии которого необходимо удалить
     */
    @Transactional
    public void deleteAllCommentsAds(Integer adsId) {
        log.info("deleteAll comment ads");
        commentRepository.deleteByAdsId(adsId);
    }

    /**
     * Обновляет комментарий для определенного объявления.
     *
     * @param adsId Идентификатор объявления
     * @param commentId Идентификатор комментария
     * @param commentDTO Обновленные данные комментариев
     * @return Обновленные данные комментария в виде объекта CommentDTO
     */
    public CommentDTO editComment(Integer adsId, Integer commentId, CommentDTO commentDTO) {
        log.info("updateComment method");
        Comment updatedComment = commentRepository.findByIdAndAds_Id(commentId, adsId).orElseThrow();
        updatedComment.setText(commentDTO.getText());
        commentRepository.save(updatedComment);

        return commentMapper.toCommentDTO(updatedComment);
    }


}
