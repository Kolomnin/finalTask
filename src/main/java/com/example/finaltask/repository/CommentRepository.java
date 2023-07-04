package com.example.finaltask.repository;

import com.example.finaltask.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByAds_Id(Integer id);

    List<Comment> deleteAllById(Integer id);

    void deleteByAdsId(Integer adsId);
}