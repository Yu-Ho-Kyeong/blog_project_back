package com.blog.blogback.Dto.Admin;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.blog.blogback.Entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminRequestDto {      // 회원 정보수정 
    private Long userNo;
    private String name;
    private String introduction;
    private String blogName;
    private String imgPath;

    public User toEntity() {
        return User.builder()
            .name(name)
            .introduction(introduction)
            .blogName(blogName)
            .imgPath(imgPath)
            .build();
    }
}
