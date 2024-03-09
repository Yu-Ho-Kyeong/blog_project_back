package com.blog.blogback.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    //private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 이름(username)을 기반으로 데이터베이스에서 사용자 정보를 조회
        CustomUserDetails user = customUserRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        // 사용자 정보를 UserDetails 객체로 변환하여 반환
        return user;
    }
}
