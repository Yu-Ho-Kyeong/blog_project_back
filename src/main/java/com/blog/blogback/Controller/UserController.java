package com.blog.blogback.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blogback.Dto.JwtToken;
import com.blog.blogback.Dto.UserDto;
import com.blog.blogback.Dto.UserIntroDto;
import com.blog.blogback.Dto.UserJoinDto;
import com.blog.blogback.Dto.UserLoginDto;

import com.blog.blogback.Dto.Admin.AdminDeleteRequestDto;
import com.blog.blogback.Dto.Admin.AdminImgRequestDto;
import com.blog.blogback.Dto.Admin.AdminRequestDto;
import com.blog.blogback.Dto.Admin.AdminResponseDto;
import com.blog.blogback.Entity.User;
import com.blog.blogback.Repository.UserRepository;
import com.blog.blogback.common.JwtTokenProvider;

import com.blog.blogback.security.CustomUserDetails;
import com.blog.blogback.security.CustomUserDetailsService;
import com.blog.blogback.security.CustomUserDtailsRepository;
import com.blog.blogback.security.SecurityUtil;
import com.blog.blogback.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDtailsRepository customUserDtailsRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/all/getUsers")
    public List<User> getMembers(){
        List<User> users = userRepository.findAll();
        return users; 
    }

    // 로그인
    @PostMapping("/all/login")  
    public Map<String, Object> login( @RequestBody UserLoginDto userLogintDto){
        String username = userLogintDto.getUserId();
        String password = userLogintDto.getUserPw();
        Optional<CustomUserDetails> loginUser = customUserDtailsRepository.findByUserId(username);
        JwtToken jwtToken = userService.login(username, password);

        Map<String, Object> param = new HashMap<>();
        if (loginUser.isPresent()) {
            param.put("user_role", loginUser.get().getAuthorities().stream().findFirst().get().getAuthority());
        }
        param.put("user_id", username);
        param.put("user_no", loginUser.get().getId());
        param.put("user_access_token", jwtToken.getAccessToken());
        param.put("user_refresh_token", jwtToken.getRefreshToken());

        log.info("jwtToken accsessToken = {}, refreshToekn = {}" + jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return param;
    }
    // refreshAccessToken 발급
    @PostMapping("/all/refreshAccessToken") 
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> requestData) {
        String refreshToken = requestData.get("refreshToken");
        // refreshToken의 유효성을 확인하고 새로운 accessToken을 발급
        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.extractUsername(refreshToken); 
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            JwtToken newAccessToken = jwtTokenProvider.generateToken(authentication);
            //log.info("refreshAccessToken()_newAccessToken : {}", newAccessToken);
            // 새로운 accessToken을 응답으로 반환
            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", newAccessToken.getAccessToken());
            //log.info("refreshAccessToken()_responseMap : {}", response);
            return ResponseEntity.ok(response);
        } else {
            // refreshToken이 유효하지 않을 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refreshToken");
        }
    }

    // 회원가입
    @PostMapping("/all/signup")    
    public ResponseEntity<UserDto> signup(@RequestBody UserJoinDto userJoinDto) {
        UserDto savedUserDto = userService.signUp(userJoinDto);
        return ResponseEntity.ok(savedUserDto);
    }
    
    @PostMapping("/admin/test") 
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }

    // 회원 소개글 정보 조회
    @GetMapping("/all/getIntro/{userNo}") 
    public String getIntro(@PathVariable Long userNo) {
        return userService.getIntro(userNo);
    }
    

    //회원 소개글 수정
    @PostMapping("/admin/updateIntro")
    public String updateIntro(@RequestBody UserIntroDto userIntroDto) {
        return userService.updateIntro(userIntroDto).getIntroduction();
        
    }

    // 회원 정보 조회 (설정페이지)
    @GetMapping("/all/getAdminSetInfo/{userNo}")
    public AdminResponseDto getAdminSetInfo(@PathVariable Long userNo, AdminResponseDto adminResponseDto) {
        return userService.getUserSetInfo(userNo, adminResponseDto);
    }

    // 회원정보[소개글, name] 수정 (설정페이지)
    @PostMapping("/admin/adminUpdateInfo")
    public User postMethodName(@RequestBody AdminRequestDto adminRequestDto) {
        return userService.adminUpdateInfo(adminRequestDto);
    }

    // 회원 정보[블로그명] 수정 (설정페이지)
    @PostMapping("/admin/adminUpdateBlogName")
    public User adminUpdateBlogName(@RequestBody AdminRequestDto adminRequestDto) {
        return userService.adminUpdateBlogName(adminRequestDto);
    }   

    // 회원 프로플사진 업로드
    @PostMapping("/admin/uploadImg")
    public String uploadImg(@ModelAttribute AdminImgRequestDto adminImgRequestDto) {
        return userService.uploadImg(adminImgRequestDto);
    }
     
    // 회원탈퇴
    @DeleteMapping("/user/deleteUser")
    public void deleteUser(@RequestBody AdminDeleteRequestDto userLoginDto){
        userService.deleteUser(userLoginDto);
    }   
}
