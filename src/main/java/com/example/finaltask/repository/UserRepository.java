package com.example.finaltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.finaltask.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Integer id);

    Optional<User> findUserByEmail(String email);

    Optional<User> findByEmail(String email);
}