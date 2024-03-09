package com.blog.blogback.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogback.Entity.Board;
import com.blog.blogback.Entity.BoardImg;

public interface BoardImgRepository extends JpaRepository<BoardImg, Long> {
    void deleteAllByBoard(Board board);
}
