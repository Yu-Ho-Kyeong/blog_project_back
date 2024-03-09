package com.blog.blogback.Repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blog.blogback.Entity.Board;
import com.blog.blogback.Entity.Tag;


@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
    //태그번호로 태그정보 조회
    Optional<Tag> findByTagNo(Long tagNo);  
    // 태그별 게시글 count
    @Query("SELECT t.tagName, COUNT(t.tagName) FROM Tag t GROUP BY t.tagName")
    List<Object[]> boardCountByTagName();
    // 모든 태그 count
    @Query("SELECT COUNT(*) FROM Tag t")
    int allTagCnt();
    // 게시글 정보로 연관된 태그 삭제
    void deleteAllByBoard(Board board);
}
