package com.example.finaltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.finaltask.model.entity.Ads;

public interface AdsRepository extends JpaRepository<Ads,Integer> {
}