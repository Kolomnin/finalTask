package com.example.finaltask.repository;

import com.example.finaltask.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Component
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByAds_Id(Integer id);

    List<Comment> deleteAllById(Integer id);

    void deleteByAdsId(Integer adsId);

    void deleteById (Integer id);
    Optional<Comment> findByIdAndAds_Id (Integer adsId, Integer commentId);
    void deleteByIdAndAds_Id (Integer adsId, Integer commentId);

    Optional<Comment> findById (Integer id);
}