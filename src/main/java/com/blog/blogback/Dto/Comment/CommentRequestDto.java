package com.blog.blogback.Dto.Comment;

import com.blog.blogback.Entity.Board;
import com.blog.blogback.Entity.Comment;
import com.blog.blogback.Entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {        // 댓글 수정 및 저장
    private Long userNo;
    private Long parentCommentNo;
    private String commentContent;

    public CommentRequestDto(String content) {
        this.commentContent = content;
    }

    public Comment toEntity(Comment parent, Board board, User user) {
        return Comment.builder()
            .board(board)
            .parent(parent)
            .user(user)
            .commentContent(commentContent)
            .build();
    }
}
