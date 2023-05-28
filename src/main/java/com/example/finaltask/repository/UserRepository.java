package com.example.finaltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.finaltask.model.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}