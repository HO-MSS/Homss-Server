package com.homss.server.mapper;

import com.homss.server.ServerApplicationTests;
import com.homss.server.common.constant.MemberConstant;
import com.homss.server.model.Comment;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.member.Member;
import com.homss.server.model.member.MemberStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.homss.server.model.member.MemberStatus.ACTIVE;
import static com.homss.server.model.member.MemberStatus.DELETED;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentMapperTest extends ServerApplicationTests {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private MemberMapper memberMapper;

    @AfterEach
    void clean() {
        commentMapper.deleteAll();
        boardMapper.deleteAll();
        memberMapper.deleteAll();

    }

    @Test
    @DisplayName("댓글 저장")
    void save_test() {
        //given
        Member member = Member.of(1L, "member", "url");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment newComment = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);

        //when
        commentMapper.save(newComment);

        //then
        assertThat(commentMapper.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글 모두 삭제")
    void deleteAll_test() {
        //given
        Member member = Member.of(1L, "member", "url");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment comment = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(comment);

        //when
        commentMapper.deleteAll();

        //then
        assertThat(commentMapper.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("댓글 모두 조회")
    void findAll_test() {
        //given
        Member member = Member.of(1L, "member", "url");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment comment1 = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        Comment comment2 = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(comment1);
        commentMapper.save(comment2);

        //when
        List<Comment> allComment = commentMapper.findAll();

        //then
        assertThat(allComment.size()).isEqualTo(2);
    }


}
