package com.blog.blogback.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blogback.service.BoardService;
import com.blog.blogback.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/uploads")
public class UploadController {

    private final UserService userService;
    private final BoardService boardService;

    // docker container에서 upload된 유저 프로필 이미지 불러오기
    @GetMapping("/profile/{imageName}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable String imageName) {
        try {
            byte[] imageBytes = userService.getImage(imageName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } catch (IOException e) {
            // 이미지를 찾을 수 없을 때 예외 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // docker container에서 upload된 게시글 이미지 불러오기
    @GetMapping("/board/{imageName}")
    public ResponseEntity<byte[]> getBoardImage(@PathVariable String imageName) {
        try {
            byte[] imageBytes = boardService.getImage(imageName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } catch (IOException e) {
            // 이미지를 찾을 수 없을 때 예외 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
