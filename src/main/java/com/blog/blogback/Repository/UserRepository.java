package com.blog.blogback.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.blogback.Entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    //userId로 user 정보 조회
    Optional<User> findByUserId(String userId);
}

