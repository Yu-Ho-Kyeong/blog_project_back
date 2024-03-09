package com.blog.blogback.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.blog.blogback.Entity.Board;
import com.blog.blogback.Entity.Tag;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagRequestDto {        // 태그저장
    private Long tagNo;
    private String tagName;
    private LocalDateTime regDate;
    private Board board;

    public Tag toEntity(){
        return Tag.builder()
            .tagNo(tagNo)
            //.boardNo(boardNo)
            .tagName(tagName)
            .board(board)
            .regDate(LocalDateTime.now())
            .build();
    }  
}
