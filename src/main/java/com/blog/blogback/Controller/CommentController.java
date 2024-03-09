package com.blog.blogback.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blogback.Dto.Comment.CommentRequestDto;
import com.blog.blogback.Dto.Comment.CommentResponseDto;
import com.blog.blogback.service.CommentService;
import com.blog.blogback.Entity.Comment;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    // 게시물 댓글 count
    @GetMapping("/all/getCount/{boardNo}")
    public int getCount(@PathVariable Long boardNo) {
        return commentService.getCountComment(boardNo);
    }

    // 게시물 자식댓글 count
    @GetMapping("/all/getReCount/{boardNo}/{parentCommentNo}")
    public int getReCount(@PathVariable Long boardNo, @PathVariable Long parentCommentNo) {
        return commentService.getReCommentCountByCommentNo(boardNo, parentCommentNo);
    }
    
    // 댓글 조회
    @GetMapping("/all/getComment/{boardNo}")
    public List<CommentResponseDto> getComment(@PathVariable Long boardNo) {
        return commentService.findCommentByBoardNo(boardNo);
    }

    // 댓글 작성
    @PostMapping("/user/addComment/{boardNo}")    
    public Comment saveComment(@PathVariable Long boardNo, @RequestBody CommentRequestDto commentRequestDto) {
        Comment comment = commentService.addComment(boardNo, commentRequestDto);
        return comment;  
    }

    // 댓글 수정
    @PutMapping("/user/modifyComment/{commentNo}")
    public Long modifyComment(@PathVariable Long commentNo, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.modifyComment(commentNo, commentRequestDto).getId();
    }

    // 댓글 삭제
    @DeleteMapping("/user/deleteComment/{commentNo}")
    public void deleteComment(@PathVariable Long commentNo){
        commentService.deleteComment(commentNo);
    }
}
