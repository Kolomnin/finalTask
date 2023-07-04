package com.example.finaltask.repository;

import com.example.finaltask.model.entity.User;
import com.example.finaltask.model.entity.UserAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<UserAvatar,Integer> {

//    UserAvatar getById();

    Optional<UserAvatar> findById(Integer id);



}
