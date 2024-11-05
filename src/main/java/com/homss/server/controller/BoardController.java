package com.homss.server.controller;

import com.homss.server.common.annotation.CurrentMemberId;
import com.homss.server.dto.request.BoardRequest;
import com.homss.server.dto.response.BoardSaveResponse;
import com.homss.server.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
