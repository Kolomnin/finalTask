package com.example.finaltask.repository;

import com.example.finaltask.model.entity.Ads;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    @NonNull
    List<Ads> findAll();
    Optional<Ads> findById(Long id);

    void deleteById(Long id);
}