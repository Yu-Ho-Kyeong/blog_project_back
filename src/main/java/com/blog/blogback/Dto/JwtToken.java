package com.blog.blogback.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType;       // 인증 타입
    private String accessToken;     // 액세스 토큰
    private String refreshToken;    // 리프레시 토큰
}
