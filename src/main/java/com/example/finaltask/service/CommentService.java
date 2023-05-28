package com.example.finaltask.service;

import org.springframework.stereotype.Service;
import com.example.finaltask.repository.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }




}