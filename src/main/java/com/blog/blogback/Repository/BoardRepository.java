package com.blog.blogback.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.blog.blogback.Entity.Board;
import com.blog.blogback.Entity.BoardImg;
import com.blog.blogback.Entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
    //게시글 조회
    Optional<Board> findByBoardNo(Long boardNo);   
    //이전 게시글 정보 조회
    @Query("SELECT MAX(b.boardNo) AS preBoardNo, b.boardTitle AS boardTitle FROM Board b WHERE b.boardNo < :boardNo")
    Map<String, Object> findPreByBoardNo(Long boardNo);   
    //다음 게시글 정보 조회
    @Query("SELECT MIN(b.boardNo) AS nextBoardNo, b.boardTitle AS boardTitle FROM Board b WHERE b.boardNo > :boardNo")
    Map<String, Object> findNextByBoardNo(Long boardNo);   
    //게시글번호 max값 찾기
    @Query("SELECT MAX(b.boardNo) FROM Board b")        
    Long findMaxBoardNo();
    //게시글번호 min값 찾기
    @Query("SELECT MIN(b.boardNo) FROM Board b")
    Long findMinBoardNo();
    //태그 및 게시글 조회
    @Query("SELECT b, CONCAT(t.tagName, ',') FROM Board b JOIN b.tags t GROUP BY b")
    List<Board> findAllWithTags(Sort sort);
    //태그 및 게시글 조회
    @Query("SELECT b FROM Board b")
    Page<Board> findAllBoard(Pageable Pageable);
    //태그별 게시글 조회
    @Query("SELECT b, t.tagName FROM Board b JOIN b.tags t WHERE t.tagName = :tagName GROUP BY b")
    Page<Object[]> findBoardWithTags(String tagName, Pageable Pageable);
    // user정보로 게시글 삭제
    void deleteAllByUser(User user);
    // user정보로 연관된 게시글 리스트 조회
    List<Board> findByUser(User user);
    //게시글 이미지 저장
    BoardImg save(BoardImg boardImg);
    
}
