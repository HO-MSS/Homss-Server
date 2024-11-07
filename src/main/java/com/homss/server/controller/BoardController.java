package com.homss.server.controller;

import com.homss.server.common.annotation.CurrentMemberId;
import com.homss.server.dto.request.BoardRequest;
import com.homss.server.dto.response.BoardListResponse;
import com.homss.server.dto.response.BoardSaveResponse;
import com.homss.server.model.board.BoardType;
import com.homss.server.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping()
    public ResponseEntity<BoardSaveResponse> saveBoard(@CurrentMemberId Long memberId,
                                                       @Valid @RequestBody BoardRequest request) {
        BoardSaveResponse response = boardService.saveBoard(memberId, request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<BoardListResponse> findAllBoardWithType(@Valid @RequestParam("type") BoardType boardType, Pageable pageable) {
        BoardListResponse response = boardService.findAllBoardWithType(boardType, pageable);
        return ResponseEntity.ok().body(response);
    }
}
