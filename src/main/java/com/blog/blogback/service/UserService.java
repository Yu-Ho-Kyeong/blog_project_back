package com.blog.blogback.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.blogback.Dto.JwtToken;
import com.blog.blogback.Dto.UserDto;
import com.blog.blogback.Dto.UserIntroDto;
import com.blog.blogback.Dto.UserJoinDto;
import com.blog.blogback.Entity.Board;
import com.blog.blogback.Entity.User;
import com.blog.blogback.Repository.BoardImgRepository;
import com.blog.blogback.Repository.BoardRepository;
import com.blog.blogback.Repository.CommentRepository;
import com.blog.blogback.Repository.TagRepository;
import com.blog.blogback.Repository.UserRepository;
import com.blog.blogback.common.JwtTokenProvider;
import com.blog.blogback.common.Exception.NotFoundException;
import com.blog.blogback.common.Exception.ErrorType;

import com.blog.blogback.Dto.Admin.AdminDeleteRequestDto;
import com.blog.blogback.Dto.Admin.AdminImgRequestDto;
import com.blog.blogback.Dto.Admin.AdminRequestDto;
import com.blog.blogback.Dto.Admin.AdminResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@PropertySource(value = {"classpath:jdbc.properties"})
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    
    @Value("${upload.profile.path}")        // 유저 프로필이미지 업로드 경로
    private String uploadDir;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final BoardImgRepository boardImgRepository;

    // 회원가입
    @Transactional
    public UserDto signUp(UserJoinDto userJoinDto) {
        Optional<User> optionalUser = userRepository.findByUserId(userJoinDto.getUserId());
        if (optionalUser.isPresent()) {
            throw new NotFoundException("이미 가입된 사용자입니다.");
        }

        String encodedPassword = passwordEncoder.encode(userJoinDto.getUserPw());
        String role = "ROLE_USER";
        return UserDto.toDto(userRepository.save(userJoinDto.toEntity(encodedPassword, role)));
    }

    // 로그인
    @Transactional
    public JwtToken login(String username, String password){
        // userId와 userPw를  기반으로 Authentication 객체 생성,
        // 이떄 authentication은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new 
        UsernamePasswordAuthenticationToken(username, password);
        System.out.println("authenticationToken : " + authenticationToken );

        // 실제 검증 authenticate() 매서드를 통해 요청된 User에 대한 검증 진행
        // authenticate 매서드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 매서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        System.out.println("jwtToken : " + jwtToken);
        return jwtToken;
    }

    // 소개글 정보 조회
    @Transactional
    public String getIntro(Long userNo){
        User user = userRepository.findById(userNo)
            .orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다. id=" + userNo));
        return user.getIntroduction();
    }

    // 소개글 수정
    @Transactional
    public User updateIntro(UserIntroDto userIntroDto) {
        User user = userRepository.findById(userIntroDto.getUserNo())
            .orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다. userNo=" + userIntroDto.getUserNo()));
        
            user.update(userIntroDto.getIntroduction());
        return user;
    }

    // 유저 정보 조회
    @Transactional
    public AdminResponseDto getUserSetInfo(Long userNo, AdminResponseDto adminResponseDto){
        User user = userRepository.findById(userNo)
            .orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다. userNo=" + userNo));
        adminResponseDto = new AdminResponseDto(user);
        return adminResponseDto;
    }

    // 유저설정 변경
    @Transactional
    public User adminUpdateInfo(AdminRequestDto adminRequestDto){
        User user = userRepository.findById(adminRequestDto.getUserNo())
            .orElseThrow(()-> new NotFoundException("해당 회원을 찾을 수 없습니다. userNo="+adminRequestDto.getUserNo()));
        user.updateInfo(adminRequestDto.getName(), adminRequestDto.getIntroduction());
        return user;
    }

    // 유저설정 변경
    @Transactional
    public User adminUpdateBlogName(AdminRequestDto adminRequestDto){
        User user = userRepository.findById(adminRequestDto.getUserNo())
            .orElseThrow(()-> new NotFoundException("해당 회원을 찾을 수 없습니다. userNo="+adminRequestDto.getUserNo()));
        user.updateBlogName(adminRequestDto.getBlogName());
        return user;
    }

    // 유저 프로필 이미지 업로드
    @Transactional
    public String uploadImg(AdminImgRequestDto adminImgRequestDto){

        User user = userRepository.findById(adminImgRequestDto.getUserNo()).
            orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다. id="+ adminImgRequestDto.getUserNo()));
  
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            try{
                Files.createDirectories(uploadPath);
            }catch(IOException e){
                throw new NotFoundException("디렉토리 생성 실패");
            }
        }

        // 파일명 중복 방지를 위한 날짜시간 생성
		LocalDateTime now = LocalDateTime.now();
		String fmtNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        
        String fileName = fmtNow + "_" + adminImgRequestDto.getFile().getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        try{
            Files.copy(adminImgRequestDto.getFile().getInputStream(), filePath);
            
            String[] pathPart = filePath.toString().split("public");
            String fakePath = pathPart[1];

            user.updateImgPath(fakePath);
            userRepository.save(user);
        }catch(IOException e){
            throw new NotFoundException("파일 업로드 실패");
        }

        return user.getImgPath();
    }

    //회원탈퇴
    @Transactional
    public void deleteUser(AdminDeleteRequestDto userLoginDto){
        
        User user = userRepository.findById(userLoginDto.getUserNo())
            .orElseThrow(()-> new NotFoundException("해당 유저를 찾을 수 없습니다." + userLoginDto.getUserNo()));
        
        String userPwd = user.getUserPw();
       
        if(!passwordEncoder.matches( userLoginDto.getUserPw(), userPwd)){
            throw new NotFoundException(ErrorType.NOT_MATCHING_PASSWORD);
        }

        List<Board> userBoards =null;
        //log.info("해당 user의 게시글 찾아서 삭제 : " + user.getUserId());
        userBoards = boardRepository.findByUser(user);
        for(Board board : userBoards){
            tagRepository.deleteAllByBoard(board);
            boardImgRepository.deleteAllByBoard(board);
            boardRepository.deleteAllByUser(user);
            
        }    

        commentRepository.deleteAllByUser(user);
        userRepository.delete(user);  
    }
}