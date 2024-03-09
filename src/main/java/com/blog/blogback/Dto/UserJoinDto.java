package com.blog.blogback.Dto;

import com.blog.blogback.Entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserJoinDto {
    private String userId;
    private String userPw; 
    private String name;
    private String introduction;
    private String imgPath;
    private String role;
    private String blogName;

    public User toEntity(String encodedPassword, String role) {
        
        return User.builder()
            .userId(userId)
            .userPw(encodedPassword)
            .role(role)
            .name(name)
            .blogName(userId+".log")
            .introduction(introduction)
            .imgPath("/img/profile/defaultProfile.png")
            .build();
    }

}
