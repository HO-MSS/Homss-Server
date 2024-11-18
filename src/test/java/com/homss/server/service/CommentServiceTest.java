package com.homss.server.service;

import com.homss.server.ServerApplicationTests;
import com.homss.server.common.exception.ApplicationException;
import com.homss.server.dto.request.CommentEditRequest;
import com.homss.server.dto.request.CommentRequest;
import com.homss.server.dto.response.CommentResponse;
import com.homss.server.dto.response.LikeResponse;
import com.homss.server.mapper.BoardMapper;
import com.homss.server.mapper.CommentLikeMapper;
import com.homss.server.mapper.CommentMapper;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.comment.Comment;
import com.homss.server.model.comment.CommentLike;
import com.homss.server.model.comment.CommentStatus;
import com.homss.server.model.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

import static com.homss.server.common.exception.ExceptionCode.NOT_COMMENT_AUTHOR_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CommentServiceTest extends ServerApplicationTests {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentLikeMapper commentLikeMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private MemberMapper memberMapper;

    @AfterEach
    void clean() {
        commentLikeMapper.deleteAll();
        commentMapper.deleteAll();
        boardMapper.deleteAll();
        memberMapper.deleteAll();
    }

    @Test
    @DisplayName("댓글 등록")
    void save_test() {
        //given
        Member member = Member.of(1L, "member", "url");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        String content = "content";
        CommentRequest request = new CommentRequest(content, null);

        //when
        commentService.saveComment(member.getMemberId(), board.getBoardId(), request);

        //then
        assertThat(commentMapper.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment_test() {
        Member member = Member.of(1L, "member", "url");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment newComment = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(newComment);

        CommentStatus status = CommentStatus.DELETED;

        //when
        commentService.deleteComment(member.getMemberId(), newComment.getCommentId());

        //then
        Comment comment = commentMapper.findById(newComment.getCommentId()).orElse(null);

        assertThat(comment).isNotNull();
        assertThat(comment.getCommentStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("댓글 삭제 시 작성자가 일치하지 않으면 예외")
    void deleteComment_author_exception_test() {
        Member member1 = Member.of(1L, "member", "url");
        Member member2 = Member.of(1L, "member", "url");
        memberMapper.save(member1);
        memberMapper.save(member2);
        Board board = Board.of(member1.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment newComment = Comment.of(member1.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(newComment);

        CommentStatus status = CommentStatus.DELETED;

        //when & then
        assertThatThrownBy(() -> commentService.deleteComment(member2.getMemberId(), newComment.getCommentId()))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(NOT_COMMENT_AUTHOR_ERROR.getMessage());
    }

    @Test
    @DisplayName("댓글 수정")
    void editComment_test() {
        Member member = Member.of(1L, "member", "url");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment newComment = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(newComment);

        CommentStatus status = CommentStatus.EDITED;
        String content = "edit content";
        CommentEditRequest request = new CommentEditRequest(content);

        //when
        commentService.editComment(member.getMemberId(), newComment.getCommentId(), request);

        //then
        Comment comment = commentMapper.findById(newComment.getCommentId()).orElse(null);

        assertThat(comment).isNotNull();
        assertThat(comment.getContent()).isEqualTo(content);
        assertThat(comment.getCommentStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("댓글 수정 시 작성자가 일치하지 않으면 예외")
    void editComment_author_exception_test() {
        Member member1 = Member.of(1L, "member", "url");
        Member member2 = Member.of(1L, "member", "url");
        memberMapper.save(member1);
        memberMapper.save(member2);
        Board board = Board.of(member1.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment newComment = Comment.of(member1.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(newComment);

        String content = "edit content";
        CommentEditRequest request = new CommentEditRequest(content);

        //when & then
        assertThatThrownBy(() -> commentService.editComment(member2.getMemberId(), newComment.getCommentId(), request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(NOT_COMMENT_AUTHOR_ERROR.getMessage());
    }

    @Test
    @DisplayName("댓글 좋아요 등록")
    void postCommentLike_post_test() {
        Member member = Member.of(1L, "member", "url");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment comment = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(comment);

        //when
        LikeResponse response = commentService.postCommentLike(member.getMemberId(), comment.getCommentId());

        // then
        assertThat(response.likeStatus()).isTrue();
    }

    @Test
    @DisplayName("댓글 좋아요 삭제")
    void postCommentLike_delete_test() {
        Member member = Member.of(1L, "member", "url");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment comment = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(comment);
        CommentLike commentLike = CommentLike.of(comment.getCommentId(), member.getMemberId());
        commentLikeMapper.save(commentLike);

        //when
        LikeResponse response = commentService.postCommentLike(member.getMemberId(), comment.getCommentId());

        // then
        assertThat(response.likeStatus()).isFalse();
    }

    @Test
    @DisplayName("댓글 모두 조회")
    void getAllComment_test() {
        Member member = Member.of(1L, "member", "url");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment newComment1 = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        Comment newComment2 = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(newComment1);
        commentMapper.save(newComment2);
        Comment newComment3 = Comment.of(member.getMemberId(), board.getBoardId(), "content", newComment1.getCommentId());
        commentMapper.save(newComment3);

        //when
        List<CommentResponse> comments = commentService.getAllComment(member.getMemberId(), board.getBoardId());

        // then
        CommentResponse firstComment = comments.stream().filter(comment -> Objects.equals(comment.getCommentId(), newComment1.getCommentId()))
                .toList().get(0);
        assertThat(comments.size()).isEqualTo(2);
        assertThat(firstComment.getCommentId()).isEqualTo(newComment1.getCommentId());

    }

}
