package com.blog.blogback.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="tag")
@Entity
@NoArgsConstructor()
@Getter

public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagNo;

    @Column(name = "tag_name", length = 100)
    private String tagName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_no")
    private Board board;
    
    private LocalDateTime regDate;

    @Builder
    public Tag(Long tagNo, String tagName, LocalDateTime regDate, Board board) {
        this.tagNo = tagNo;
        this.tagName = tagName;
        this.board = board;
        this.regDate = LocalDateTime.now();
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    // 게시글 설정 메서드
    public void updateBoard(Board board) {
        this.board = board;
    }

    // 태그명 변경
    public void updateTagName(String tagName) {
        this.tagName = tagName;
    }

}
