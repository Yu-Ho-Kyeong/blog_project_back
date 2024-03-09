package com.blog.blogback.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserDtailsRepository extends JpaRepository<CustomUserDetails, Long> {
    Optional<CustomUserDetails> findByUserId(String userId);
}
