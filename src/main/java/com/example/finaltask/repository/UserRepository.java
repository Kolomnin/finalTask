package com.example.finaltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.finaltask.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

//    User findByLogin();
    User findById(Long id);
    User findByLogin(String name);
}