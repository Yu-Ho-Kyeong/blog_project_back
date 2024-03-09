package com.blog.blogback.Dto;

import com.blog.blogback.Entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// UserJoinDto로 들어온 정보를 toEntity 메서드를 통해 CustomUserDetails 엔티티로 변환한다.
// 변환한 엔티티를 DB에 저장한다. 이때, 반환값으로 저장된 CustomUserDetails 엔티티를 받는다.
// 반환받은 엔티티를 UserDto의 static method인 toDto를 호출하여 UserDto의 변환하여 반환한다.
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long userNo;
    private String userId;
    private String userPw; 
    private String name;
    private String introduction;
    private String imgPath;

    static public UserDto toDto(User user) {
        return UserDto.builder()
            .userNo(user.getUserNo())
            .userId(user.getUserId())
            .name(user.getName())
            .introduction(user.getIntroduction())
            .imgPath(user.getImgPath())
            .build();     
    }

    public User toEntity() {
        return User.builder()
            .userNo(userNo)
            .userId(userId)
            .name(name)
            .introduction(introduction)
            .imgPath(imgPath)
            .build();
    }
}