package com.como.server.controller;

import com.como.server.dto.response.BoardSimpleResponses;
import com.como.server.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping("")
    public ResponseEntity<List<BoardSimpleResponses>> getBoards(@RequestParam("type") String type) {
        List<BoardSimpleResponses> response = boardService.getBoardsWithType(type);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<String> getBoardDetail(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok().body("HI");
    }


    @PostMapping("")
    public ResponseEntity<String> createBoard() {
        return ResponseEntity.ok().body("HI");
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<String> editBoard(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok().body("HI");
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok().body("HI");
    }

}
