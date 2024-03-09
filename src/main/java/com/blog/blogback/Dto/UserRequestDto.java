package com.blog.blogback.Dto;

import com.blog.blogback.Entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {
    private Long userNo;
    private String userId;
    private String userPw; 
    private String name;
    private String introduction;
    private String imgPath;

    @Builder
    public UserRequestDto(Long userNo, String userId, String userPw, 
                            String name, String introduction, String imgPath) 
    {
        this.userNo = userNo;
        this.userId = userId;
        this.userPw = userPw;
        this.name = name;
        this.introduction = introduction;
        this.imgPath = imgPath;
    }
    public User toEntity() {
        return User.builder()
                .userNo(userNo)
                .userId(userId)
                .userPw(userPw)
                .name(name)
                .introduction(introduction)
                .imgPath(imgPath)
                .build();
    }
}
