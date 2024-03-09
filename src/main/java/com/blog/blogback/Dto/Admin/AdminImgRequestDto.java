package com.blog.blogback.Dto.Admin;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminImgRequestDto {   // 회원 프로플사진 업로드
    Long userNo;
    String imgPath;
    MultipartFile file;
}
