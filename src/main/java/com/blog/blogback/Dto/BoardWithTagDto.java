package com.blog.blogback.Dto;

import java.util.List;
import com.blog.blogback.Entity.Board;
import com.blog.blogback.Entity.BoardImg;
import com.blog.blogback.Entity.Comment;
import com.blog.blogback.Entity.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardWithTagDto {      // 태그별 게시글 정보 조회
    private Board board;
    private List<Tag> tags;
    private List<BoardImg> imgs;
    private String tagName;
}
