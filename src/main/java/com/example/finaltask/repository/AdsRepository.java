package com.example.finaltask.repository;

import com.example.finaltask.model.entity.Ads;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdsRepository extends JpaRepository<Ads, Integer> {
    @NonNull
    List<Ads> findAll();
    Ads findById(Long id);

    void deleteById(Long id);
}