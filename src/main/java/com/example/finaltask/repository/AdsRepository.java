package com.example.finaltask.repository;

import com.example.finaltask.model.entity.Ads;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    @NonNull
    List<Ads> findAll();
    Optional<Ads> findById(Integer id);

    Ads findByAuthorId (Integer id);

    Ads findByAuthorIdLoginAndId(String login,Integer id);

}