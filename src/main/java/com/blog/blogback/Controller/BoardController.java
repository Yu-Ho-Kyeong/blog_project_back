package com.blog.blogback.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blogback.Dto.BoardRequestDto;
import com.blog.blogback.Dto.BoardWithTagDto;
import com.blog.blogback.Dto.Board.BoardImgRequestDto;
import com.blog.blogback.Dto.Board.BoardImgSaveDto;
import com.blog.blogback.Entity.Board;
import com.blog.blogback.Entity.BoardImg;
import com.blog.blogback.Entity.Tag;
import com.blog.blogback.Repository.BoardRepository;
import com.blog.blogback.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.web.bind.annotation.PutMapping;

@Slf4j
@RequestMapping("/api/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

    // private final BoardRepository boardRepository;
    private final BoardService boardService;

    // 게시글 정보 조회
    @GetMapping("/all/getBoards")     
    public ResponseEntity<Page<BoardWithTagDto>> getBoards(BoardRequestDto boardRequestDto,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("regDate").descending());
        Page<BoardWithTagDto> boardPage = boardService.getBoards(boardRequestDto, pageable);

        return ResponseEntity.ok(boardPage);
    }

    // 태그별 게시글 정보 조회
    @GetMapping("/all/getBoardsWithTag/{tagName}")
    public ResponseEntity<Page<BoardWithTagDto>> getBoardsWithTag(@PathVariable String tagName,
                @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page, size, Sort.by("regDate").descending()); 
        Page<BoardWithTagDto> boardPage = boardService.getBoardsWithTag(tagName, pageable);

        return ResponseEntity.ok(boardPage);
    }

    // 특정 게시글 정보 조회
    @GetMapping("/all/getBoard/{boardNo}")
    public Map<String, Object> getBoard(@PathVariable Long boardNo) {
        return boardService.getBoard(boardNo);
    }

    // 게시글 이미지 업로드
    @PostMapping("/admin/uploadImg")  
    public List<String> uploadImg(@ModelAttribute BoardImgRequestDto boardImgRequestDto) {
        return boardService.uploadImg(boardImgRequestDto);
    }

    // 게시글 이미지 저장
    @PostMapping("/admin/saveImg")       
    public Map<String, Object> updateContentAndAdImg(@RequestBody BoardImgSaveDto boardImgSaveDto) {
        return boardService.updateContentAndAdImg(boardImgSaveDto);
    }

    // 게시글 저장
    @PostMapping("/admin/saveBoard")
    public Long saveBoard(@RequestBody BoardRequestDto boardRequestDto) { 
        Long savedBoardNo = boardService.saveBoard(boardRequestDto);
        return savedBoardNo;
    }

    // 게시글 수정
    @PutMapping("/admin/saveBoard")         
    public Board update(@RequestBody BoardRequestDto boardDto) {
        return boardService.update(boardDto);
    }

    // 게시글 삭제
    @DeleteMapping("/admin/delete/{boardNo}")     
    public void delete(@PathVariable Long boardNo) {
        boardService.delete(boardNo);
    }

    // 이전 게시글 정보 조회
    @GetMapping("/all/getPreBoard/{boardNo}")
    public ResponseEntity<?> getPreBoard(@PathVariable Long boardNo) {
        try {
            Map<String, Object> preBoard = boardService.findPreBoard(boardNo);
            Iterator<String> mapIter = preBoard.keySet().iterator();
 
            if (preBoard != null) {
                return ResponseEntity.ok(preBoard);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving previous board: " + e.getMessage());
        }
    }

    // 다음게시글 정보 조회
    @GetMapping("/all/getNextBoard/{boardNo}")
    public ResponseEntity<?> getNextBoard(@PathVariable Long boardNo) {
        try {
            Map<String, Object> nextBoard = boardService.findNextBoard(boardNo);
            Iterator<String> mapIter = nextBoard.keySet().iterator();

            if (nextBoard != null) {
                return ResponseEntity.ok(nextBoard);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving next board: " + e.getMessage());
        }
    }

    // 게시글 번호 max값 찾기
    @GetMapping("/all/maxBoardNo")
    public ResponseEntity<?> getMaxBoardNo() {
        try {
            Long maxBoardNo = boardService.findMaxBoardNo();
            if (maxBoardNo != null) {
                return ResponseEntity.ok(maxBoardNo);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving max boardNo: " + e.getMessage());
        }
    }

    // 게시글 번호 min값 찾기
    @GetMapping("/all/minBoardNo")
    public ResponseEntity<?> getMinBoardNo() {
        try {
            Long minBoardNo = boardService.findMinBoardNo();
            if (minBoardNo != null) {
                return ResponseEntity.ok(minBoardNo);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving max boardNo: " + e.getMessage());
        }
    }
}
