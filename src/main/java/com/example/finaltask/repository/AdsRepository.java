package com.example.finaltask.repository;

import com.example.finaltask.model.entity.Ads;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    @NonNull
    List<Ads> findAll();
    Ads findById(Long id);

    Ads findByAuthorId (Long id);

    Ads findByAuthorIdLoginAndId(String login,Long id);

    void deleteById(Long id);
}