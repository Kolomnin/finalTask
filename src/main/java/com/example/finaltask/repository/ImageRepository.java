package com.example.finaltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.finaltask.model.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}