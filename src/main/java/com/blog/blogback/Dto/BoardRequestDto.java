package com.blog.blogback.Dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import com.blog.blogback.Entity.Board;
import com.blog.blogback.Entity.Tag;
import com.blog.blogback.Entity.User;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {      // 게시글 저장
    private Long boardNo;
    private Long userNo;
    private String boardTitle;
    private String boardContent;
    private String imgPath;
    private List<Tag> tags;
    private LocalDateTime updDate;

    @Builder
    public BoardRequestDto(String boardTitle, String boardContent, 
                            String imgPath, LocalDateTime updDate, List<Tag> tags) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.imgPath = imgPath;
        this.tags = tags;
        this.updDate = updDate;
    }

    public Board toEntity(User user) {
        return Board.builder()
                .user(user)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .imgPath(imgPath)
                .updDate(updDate)
                .tags(tags)
                .build();
    }
}
