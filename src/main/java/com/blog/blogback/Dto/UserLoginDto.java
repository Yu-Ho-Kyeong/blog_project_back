package com.blog.blogback.Dto;

import com.blog.blogback.security.CustomUserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserLoginDto {
    private String userId;
    private String userPw;

    @Builder
    public UserLoginDto(String userId, String userPw) 
    {
        this.userId = userId;
        this.userPw = userPw;
    }
    public CustomUserDetails toEntity() {
        return CustomUserDetails.builder()
                .userId(userId)
                .userPw(userPw)
                .build();
    }
}
