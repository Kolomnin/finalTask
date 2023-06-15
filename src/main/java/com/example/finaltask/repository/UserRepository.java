package com.example.finaltask.repository;

import com.example.finaltask.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    User findByLogin();
    Optional<User> findById(Long id);
}