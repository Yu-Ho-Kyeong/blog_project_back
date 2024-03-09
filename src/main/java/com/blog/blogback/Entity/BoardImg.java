package com.blog.blogback.Entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicInsert 
@Table(name="board_img")
public class BoardImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_img_no")
    private Long boardImgNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", nullable = false)
    private Board board;
    
    @Column
    private String imgPath;

    @Column
    private LocalDateTime regDate;

    @Builder
    public BoardImg(Board board, String imgPath, LocalDateTime regDate) {
        this.board = board;
        this.imgPath = imgPath;
        this.regDate = LocalDateTime.now();
    }

    public void update(String imgPath){
        this.imgPath=imgPath;
    }

    public void updateBoard(Board board){
        this.board=board;
    }
}
