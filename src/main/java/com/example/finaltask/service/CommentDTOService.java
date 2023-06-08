package com.example.finaltask.service;

import com.example.finaltask.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentDTOService {

    private final CommentRepository commentRepository;

    public CommentDTOService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
