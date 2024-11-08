package com.homss.server.service;

import com.homss.server.dto.request.CommentRequest;
import com.homss.server.mapper.CommentMapper;
import com.homss.server.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    @Transactional
    public void saveComment(Long memberId, Long boardId, CommentRequest request) {
        Comment newComment = Comment.of(memberId, boardId, request.content(), null);
        commentMapper.save(newComment);
    }
}
