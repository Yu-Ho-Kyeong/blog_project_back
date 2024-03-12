package com.blog.blogback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.blog.blogback.common.JwtAuthenticationFilter;
import com.blog.blogback.common.JwtTokenProvider;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;

	// ALL 허용 url
    private static final String[] All_PERMIT_URL_ARR = {
		"/",
		"/error",
		"/api/all/**",			// 등록된 유저정보 불러오기
		"/api/board/all/**",	// 등록된 게시글 불러오기
		"/api/comment/all/**",	// 등록된 댓글 불러오기
		"/api/tag/all/**",		// 등록된 태그 불러오기
		"/app/uploads/**"		// 서버 업로드 이미지 불러오기;
	};

	// USER 허용 url
    private static final String[] USER_PERMIT_URL_ARR = {
		"/api/comment/user/**",		 // 회원 댓글작성
		"/api/admin/uploadImg",		 // 회원프로필수정(설정페이지)
		"/api/admin/adminUpdateInfo" // 회원정보수정(설정페이지)
	};

	// ADMIN 허용 url
    private static final String[] ADMIN_PERMIT_URL_ARR = {
		"/api/comment/user/**",		// 회원 댓글작성
		"/api/board/admin/**",	 	// 관리자 게시글 작성
		"/api/comment/admin/**",	// 
		"/api/admin/**"				// 회원 정보수정, 이미지 업로드
	};

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
       	 	httpSecurity
                // REST API이므로 basic auth 및 csrf 보안을 사용하지 않음
                .httpBasic(httpBasic ->httpBasic.disable())
                .csrf(csrf -> csrf.disable())
				
                // JWT를 사용하기 때문에 세션을 사용하지 않음
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
					// 해당 API에 대해서는 모든 요청을 허가
					.requestMatchers(All_PERMIT_URL_ARR).permitAll()
					// USER 권한 허용
					.requestMatchers(USER_PERMIT_URL_ARR).hasAnyRole("USER", "ADMIN")
					// ADMIN 권한 허용
					.requestMatchers(ADMIN_PERMIT_URL_ARR).hasRole("ADMIN")
					// 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정
					.anyRequest()
					.authenticated()
				)
		
				// JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
			return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

 }
