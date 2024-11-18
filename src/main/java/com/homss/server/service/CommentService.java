package com.homss.server.service;

import com.homss.server.common.exception.ApplicationException;
import com.homss.server.dto.request.CommentEditRequest;
import com.homss.server.dto.request.CommentRequest;
import com.homss.server.dto.response.CommentResponse;
import com.homss.server.dto.response.LikeResponse;
import com.homss.server.mapper.CommentLikeMapper;
import com.homss.server.mapper.CommentMapper;
import com.homss.server.model.comment.Comment;
import com.homss.server.model.comment.CommentLike;
import com.homss.server.model.comment.CommentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.homss.server.common.exception.ExceptionCode.COMMENT_NOT_FOUND_ERROR;
import static com.homss.server.common.exception.ExceptionCode.NOT_COMMENT_AUTHOR_ERROR;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentLikeMapper commentLikeMapper;

    @Transactional
    public void saveComment(Long memberId, Long boardId, CommentRequest request) {
        Comment newComment = Comment.of(memberId, boardId, request.content(), request.parentId());
        commentMapper.save(newComment);
    }

    @Transactional
    public void deleteComment(Long memberId, Long commentId) {
        Comment comment = commentMapper.findById(commentId)
                .orElseThrow(() -> ApplicationException.create(COMMENT_NOT_FOUND_ERROR));

        if (!comment.getMemberId().equals(memberId)) {
            throw ApplicationException.create(NOT_COMMENT_AUTHOR_ERROR);
        }

        commentMapper.changeStatus(commentId, CommentStatus.DELETED);
    }

    @Transactional
    public void editComment(Long memberId, Long commentId, CommentEditRequest request) {
        Comment comment = commentMapper.findById(commentId)
                .orElseThrow(() -> ApplicationException.create(COMMENT_NOT_FOUND_ERROR));

        if (!comment.getMemberId().equals(memberId)) {
            throw ApplicationException.create(NOT_COMMENT_AUTHOR_ERROR);
        }

        commentMapper.edit(commentId, request.content());
    }

    @Transactional
    public LikeResponse postCommentLike(Long memberId, Long commentId) {
        Boolean likeStatus = commentLikeMapper.findLikeStatus(commentId, memberId);

        if (likeStatus) {
            commentLikeMapper.deleteLike(commentId, memberId);
        } else {
            CommentLike newCommentLike = CommentLike.of(commentId, memberId);
            commentLikeMapper.save(newCommentLike);
        }

        return new LikeResponse(!likeStatus);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllComment(Long memberId, Long boardId) {
        return commentMapper.findAllByBoardId(memberId, boardId);
    }
}
