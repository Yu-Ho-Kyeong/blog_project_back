package com.blog.blogback.Dto.Board;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardImgSaveDto {      // 게시글 이미지경로 및 boardContent 저장
    private Long boardNo;
    //private String imgPath;
    private String boardContent;
    private List<String> imgPathList;
}
