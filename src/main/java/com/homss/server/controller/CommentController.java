package com.homss.server.controller;

import com.homss.server.common.annotation.CurrentMemberId;
import com.homss.server.dto.request.CommentRequest;
import com.homss.server.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public ResponseEntity<Void> saveComment(@CurrentMemberId Long memberId,
                                            @PathVariable Long boardId,
                                            @RequestBody CommentRequest request) {
        commentService.saveComment(memberId, boardId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> saveComment(@CurrentMemberId Long memberId, @PathVariable Long commentId) {
        commentService.deleteComment(memberId, commentId);
        return ResponseEntity.ok().build();
    }

}
