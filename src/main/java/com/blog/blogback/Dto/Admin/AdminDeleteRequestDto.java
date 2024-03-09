package com.blog.blogback.Dto.Admin;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class AdminDeleteRequestDto {    // 회원 탈퇴
    private Long userNo;
    private String userPw;

}
