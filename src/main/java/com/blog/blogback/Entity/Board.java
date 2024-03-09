package com.blog.blogback.Entity;

import jakarta.persistence.CascadeType;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicInsert 
@Table(name="board")

public class Board {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_user_id", referencedColumnName = "userId")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tag> tags;

    @Column(nullable = false)
    private String boardTitle;

    @Column
    private String boardContent;
    
    @JsonIgnore
    @OneToMany(mappedBy = "board", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BoardImg> imgPathList = new ArrayList<>();   

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime updDate;

    @Builder
    public Board(User user, String boardTitle, 
                    String boardContent, String imgPath, LocalDateTime regDate,
                    LocalDateTime updDate, List<Tag> tags, List<Comment> childrenList) {
        
        Assert.hasText(boardTitle, "boardTitle must not be empty");
        this.user = user;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.regDate = LocalDateTime.now();
        this.updDate = updDate;

        if(tags != null){
          for(Tag tag : tags){
            tag.setBoard(this);
          }
          this.tags = tags;
        }
  }


  public void addTag(Tag tag) {
      if (tags == null) {
          tags = new ArrayList<>();
      }
      tags.add(tag);
      tag.setBoard(this);
  }

  public void update(String title, String content){
    this.boardTitle = title;
    this.boardContent = content;
    this.updDate = LocalDateTime.now();
  }

  public void updateContent(String content){
    this.boardContent = content;
  }

}
