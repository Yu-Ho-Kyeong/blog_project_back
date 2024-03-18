package com.blog.blogback.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="users") 
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    @Column(name="userId", nullable = false, unique = true)
    private String userId;
    @Column(name="userPw", nullable = false)
    private String userPw; 
    private String name;
    private String introduction;
    private String blogName;
    private LocalDateTime regDate;
    private LocalDateTime updDate;
    private String imgPath;
    private char enabled;
    private String role;

    @Builder
    public User(Long userNo, String userId, String userPw, String role, String name, 
                String introduction, String blogName, 
                String imgPath, LocalDateTime regDate, char enabled) 
    {
        this.userId = userId;
        this.userPw = userPw;
        this.role = role;
        this.name = name;
        this.introduction = introduction;
        if(blogName != null){
            this.blogName = userId+".log";
        }else{
            this.blogName = blogName;
        }
        this.imgPath = imgPath;
        this.regDate = LocalDateTime.now();
        this.enabled = '1';
    }

    public void update(String introduction) {
        this.introduction = introduction;
    }

    public void updateInfo(String name, String introduction){
        this.name = name;
        this.introduction = introduction;
    }

    public void updateBlogName(String blogName){
        this.blogName = blogName;
    }

    public void updateImgPath(String imgPath){
        this.imgPath = imgPath;
    }
}
