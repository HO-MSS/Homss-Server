package com.homss.server.mapper;

import com.homss.server.ServerApplicationTests;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardLike;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.comment.Comment;
import com.homss.server.model.comment.CommentLike;
import com.homss.server.model.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentLikeMapperTest extends ServerApplicationTests {

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
    @DisplayName("댓글 좋아요 모두 삭제")
    void deleteAll_test() {
        // given
        Member newMember = Member.create(1L);
        memberMapper.save(newMember);
        Board newBoard = Board.of(newMember.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(newBoard);
        Comment newComment = Comment.of(newMember.getMemberId(), newBoard.getBoardId(), "content", null);
        commentMapper.save(newComment);

        CommentLike newCommentLike = CommentLike.of(newComment.getCommentId(), newMember.getMemberId());
        commentLikeMapper.save(newCommentLike);

        // when
        commentLikeMapper.deleteAll();

        // then
        assertThat(commentLikeMapper.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("댓글 좋아요 모두 조회")
    void findAll_test() {
        // given
        Member newMember = Member.create(1L);
        memberMapper.save(newMember);
        Board newBoard = Board.of(newMember.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(newBoard);
        Comment newComment1 = Comment.of(newMember.getMemberId(), newBoard.getBoardId(), "content", null);
        Comment newComment2 = Comment.of(newMember.getMemberId(), newBoard.getBoardId(), "content", null);
        commentMapper.save(newComment1);
        commentMapper.save(newComment2);

        CommentLike commentLike1 = CommentLike.of(newComment1.getCommentId(), newMember.getMemberId());
        CommentLike commentLike2 = CommentLike.of(newComment2.getCommentId(), newMember.getMemberId());
        commentLikeMapper.save(commentLike1);
        commentLikeMapper.save(commentLike2);

        // when
        List<CommentLike> allCommentLike = commentLikeMapper.findAll();

        // then
        assertThat(allCommentLike.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("댓글 좋아요 저장")
    void save_test() {
        // given
        Member newMember = Member.create(1L);
        memberMapper.save(newMember);
        Board newBoard = Board.of(newMember.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(newBoard);
        Comment newComment = Comment.of(newMember.getMemberId(), newBoard.getBoardId(), "content", null);
        commentMapper.save(newComment);

        CommentLike commentLike = CommentLike.of(newComment.getCommentId(), newMember.getMemberId());

        // when
        commentLikeMapper.save(commentLike);

        // then
        assertThat(commentLikeMapper.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글 좋아요 상태 조회")
    void findBoardLikeStatus_test() {
        // given
        Member newMember = Member.create(1L);
        memberMapper.save(newMember);
        Board newBoard = Board.of(newMember.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(newBoard);
        Comment newComment = Comment.of(newMember.getMemberId(), newBoard.getBoardId(), "content", null);
        commentMapper.save(newComment);

        CommentLike commentLike = CommentLike.of(newComment.getCommentId(), newMember.getMemberId());
        commentLikeMapper.save(commentLike);

        // when
        Boolean likeStatus = commentLikeMapper.findLikeStatus(newComment.getCommentId(), newMember.getMemberId());

        // then
        assertThat(likeStatus).isTrue();
    }

    @Test
    @DisplayName("댓글 좋아요 삭제")
    void deleteLike_test() {
        // given
        Member newMember = Member.create(1L);
        memberMapper.save(newMember);
        Board newBrand = Board.of(newMember.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(newBrand);

        Comment comment1 = Comment.of(newMember.getMemberId(), newBrand.getBoardId(), "content", null);
        Comment comment2 = Comment.of(newMember.getMemberId(), newBrand.getBoardId(), "content", null);
        commentMapper.save(comment1);
        commentMapper.save(comment2);

        CommentLike commentLike1 = CommentLike.of(comment1.getCommentId(), newMember.getMemberId());
        CommentLike commentLike2 = CommentLike.of(comment2.getCommentId(), newMember.getMemberId());
        commentLikeMapper.save(commentLike1);
        commentLikeMapper.save(commentLike2);

        // when
        commentLikeMapper.deleteLike(comment1.getCommentId(), newMember.getMemberId());

        // then
        assertThat(commentLikeMapper.findAll().size()).isEqualTo(1);
    }

}
