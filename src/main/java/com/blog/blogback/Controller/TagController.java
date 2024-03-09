package com.blog.blogback.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blogback.Dto.BoardRequestDto;
import com.blog.blogback.Entity.Tag;
import com.blog.blogback.Repository.TagRepository;
import com.blog.blogback.service.BoardService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/api/tag")
@RestController
public class TagController {
    
    @Autowired private TagRepository tagRepository;
    @Autowired private BoardService boardService;

    // 모든 태그 개수 count
    @GetMapping("/all/allTagCnt")
    public int allTagCnt(){
        int cnt = tagRepository.allTagCnt();
        return cnt;
    }

    @GetMapping("/all/getTag")
    public List<Object[]> getTags(){
        List<Object[]> tagInfo = boardService.getTag();
        return tagInfo;
    }

    @GetMapping("/all/{tagNo}")
    public ResponseEntity<?> getTag(@PathVariable Long tagNo) {
        try {
            Optional<Tag> tagOptional = tagRepository.findByTagNo(tagNo);
            if(tagOptional.isPresent()){
                Tag tag = tagOptional.get();
                return new ResponseEntity<>(tag, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("There is no data", HttpStatus.BAD_REQUEST);
    }

 
}
