package com.blog.blogback.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/* 
Hibernate에서 엔티티를 데이터베이스에 삽입할 때 자동으로 null이거나 기본값인 필드를 제외하고,  
데이터베이스에 실제로 있는 필드만을 삽입
*/
@DynamicInsert 
@Table(name="comment")
@Entity

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long id;  
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_user_id", referencedColumnName = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_no")
    @JsonIgnore
    private Comment parent;               

    //@JsonIgnore
    @OneToMany(mappedBy = "parent", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> children = new ArrayList<>();   

    @Column(nullable = false)
    private String commentContent;  
    
    @Column(columnDefinition = "TINYINT", length = 1)
    private int isSecret;
               
    @ColumnDefault("FALSE")
    @Column
    private Boolean isDeleted;            
    
    @Column
    private LocalDateTime regDate;        

    @Column
    private LocalDateTime updDate;        

    @Builder
    public Comment(Board board, String commentContent, User user, Comment parent,
                    List<Comment> children, LocalDateTime regDate, Boolean isDeleted, int isSecret) {

        this.board = board;
        this.user = user;
        this.parent = parent;
        this.commentContent = commentContent;
        this.children = children;
        this.isDeleted = isDeleted;
        this.isSecret = isSecret;
        this.regDate = LocalDateTime.now();
  }

  public void update(String content){
    this.commentContent = content;
   
  }

}
