package com.blog.blogback.Dto.Admin;
import com.blog.blogback.Entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminResponseDto {   // 회원 정보조회
    private String name;
    private String introduction;
    private String blogName;
    private String imgPath;

    public AdminResponseDto(User user){
      this.name = user.getName();
      this.introduction = user.getIntroduction();
      this.blogName = user.getBlogName();
      this.imgPath = user.getImgPath();
    }
}
