package com.blog.blogback.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blog.blogback.Entity.Comment;
import com.blog.blogback.Entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
  
    // 게시글별 전체 댓글 수 count
    @Query("SELECT count(c) FROM Comment c WHERE c.board.boardNo = :boardNo")
    int countCommentByBoardNo(Long boardNo);

    // 게시글별 댓글별 자식댓글수 count
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.board.boardNo = :boardNo AND c.parent.id = :parentCommentNo")     
    int countCommentByBoardNoAndParentCommentNo(Long boardNo, Long parentCommentNo);

    // 게시글 별 댓글 정보 조회
    @Query("SELECT c FROM Comment c " +
           "LEFT JOIN FETCH c.parent " +
           "WHERE c.board.boardNo = :boardNo " +
           "ORDER BY c.parent.id ASC NULLS FIRST, c.regDate ASC")
    List<Comment> findCommentByBoardNo(Long boardNo);

    // userId로 연관된 댓글 찾기
    Optional<Comment> findByUserUserId(String userId);
    // user 정보로 댓글 삭제
    void deleteAllByUser(User user);

}
