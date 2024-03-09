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
public class UserIntroDto {
    private Long userNo;
    private String introduction; 

    public User toEntity(String introduction) {
        
        return User.builder()
            .introduction(introduction)
            .build();
    }
}
