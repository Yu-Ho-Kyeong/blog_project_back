package com.blog.blogback.Dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.blog.blogback.Dto.UserDto;
import com.blog.blogback.Entity.Board;
import com.blog.blogback.Entity.Comment;
import com.blog.blogback.Entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;



@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {   // 댓글 정보 조회
  
    private Long id;  
    private Long boardNo;           
    private Comment parent;              
    private Long parentCommentNo;
    private String commentContent;      
    private User user;      
    private List<CommentResponseDto> children = new ArrayList<>(); // children 필드 초기화
    private int isSecret;     
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDate;     

    public CommentResponseDto(Comment comment){
      this.id = comment.getId();
      this.boardNo = comment.getBoard().getBoardNo();
      this.parent = comment.getParent();
      this.commentContent = comment.getCommentContent();
      this.user = comment.getUser();
      this.regDate = comment.getRegDate();
      this.isSecret = comment.getIsSecret();
    }  
}
