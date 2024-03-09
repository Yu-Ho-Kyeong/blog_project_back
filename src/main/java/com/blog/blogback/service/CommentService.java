package com.blog.blogback.service;

import org.springframework.stereotype.Service;

import com.blog.blogback.Dto.Comment.CommentRequestDto;
import com.blog.blogback.Dto.Comment.CommentResponseDto;
import com.blog.blogback.Entity.Board;
import com.blog.blogback.Entity.Comment;
import com.blog.blogback.Entity.User;
import com.blog.blogback.Repository.BoardRepository;
import com.blog.blogback.Repository.CommentRepository;
import com.blog.blogback.Repository.UserRepository;
import com.blog.blogback.common.Exception.NotFoundException;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor    //final or NonNull 옵션 필드를 전부 포함한 생성자 만들어줌
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 댓글 count
    public int getCountComment(Long boardNo) {
        return commentRepository.countCommentByBoardNo(boardNo);
    }

    // 자식댓글 count
    public int getReCommentCountByCommentNo(Long boardNo,Long parentCommentNo) {
        return commentRepository.countCommentByBoardNoAndParentCommentNo(boardNo, parentCommentNo);
    }

    // 댓글정보 조회
    public List<CommentResponseDto> findCommentByBoardNo(Long boardNo) {
        List<CommentResponseDto> dto = convertToDto(commentRepository.findCommentByBoardNo(boardNo));
        return dto;
    }
 
    // 댓글 작성
    @Transactional  //db 트랜잭션 자동으로 commit 해줌
    public Comment addComment(Long boardNo,CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(commentRequestDto.getUserNo())
            .orElseThrow(() -> new NotFoundException("Could not found userNo " + commentRequestDto.getUserNo()));
        Board board = boardRepository.findById(boardNo)
            .orElseThrow(() -> new NotFoundException("Could not found boardNo : " + boardNo));

        Comment parent = null;
        if(commentRequestDto.getParentCommentNo() != null){ // 자식댓글
            // 부모댓글 조회
            parent = commentRepository.findById(commentRequestDto.getParentCommentNo())
                .orElseThrow(() -> new NotFoundException("Could not found parentCommentNo : " + commentRequestDto.getParentCommentNo()));
        }

        Comment comment = commentRequestDto.toEntity(parent, board, user);
        return commentRepository.save(comment);
    }

    // 댓글 수정
    @Transactional  
    public Comment modifyComment(Long id, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 댓글을 찾을 수 없습니다. id=" + id));

        //JPA의 영속성 컨텍스트 덕분에 entity 객체의 값만 변경하면 자동으로 변경사항 반영함!
        //따라서 repository.update 를 쓰지 않아도 됨.
        comment.update(commentRequestDto.getCommentContent());
        return comment;
    }
    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentNo){
        commentRepository.deleteById(commentNo);
    }

    // entity-> dto 변환
    private List<CommentResponseDto> convertToDto(List<Comment> comments) {
        List<CommentResponseDto> result = new ArrayList<>();
        Map<Long, CommentResponseDto> map = new HashMap<>();

        comments.forEach(comment -> {
            CommentResponseDto dto = new CommentResponseDto(comment);
            map.put(dto.getId(), dto);
        });
        
        comments.forEach(comment -> {
            CommentResponseDto dto = map.get(comment.getId());
            if (comment.getParent() != null) {
                CommentResponseDto parentDto = map.get(comment.getParent().getId());
                parentDto.getChildren().add(dto);
            } else {
                result.add(dto);
            }
        });
        return result;
    }
}
