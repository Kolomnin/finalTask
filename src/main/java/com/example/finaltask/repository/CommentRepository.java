package com.example.finaltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.finaltask.model.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Comment findById(Long id);
}