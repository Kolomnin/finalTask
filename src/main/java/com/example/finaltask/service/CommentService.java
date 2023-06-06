package com.example.finaltask.service;

import com.example.finaltask.model.dto.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    List<CommentDTO> getComments();

    CommentDTO postComment();

    void deleteComment();

    CommentDTO patchComment();

}