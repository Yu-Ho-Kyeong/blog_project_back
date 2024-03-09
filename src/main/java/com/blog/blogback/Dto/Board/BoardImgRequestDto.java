package com.blog.blogback.Dto.Board;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardImgRequestDto {   // 게시글 이미지 업로드
    private Long boardNo;
    private MultipartFile[] files;
}
