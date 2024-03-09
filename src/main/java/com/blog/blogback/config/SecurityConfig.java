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
		"/api/all/**",
		"/api/board/all/**",
		"/api/comment/all/**",
		"/api/tag/all/**"
	};

	// USER 허용 url
    private static final String[] USER_PERMIT_URL_ARR = {
		"/api/comment/user/**",
		"/api/admin/uploadImg",		 // 회원프로필수정(설정페이지)
		"/api/admin/adminUpdateInfo" // 회원정보수정(설정페이지)
	};

	// ADMIN 허용 url
    private static final String[] ADMIN_PERMIT_URL_ARR = {
		"/api/comment/user/**",
		"/api/board/admin/**",	 	
		"/api/comment/admin/**",		
		"/api/board/admin/**",			
		"/api/admin/**"
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
