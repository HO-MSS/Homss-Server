package com.homss.server.service;

import com.homss.server.ServerApplicationTests;
import com.homss.server.dto.request.CommentRequest;
import com.homss.server.mapper.BoardMapper;
import com.homss.server.mapper.CommentMapper;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.Comment;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentServiceTest extends ServerApplicationTests {

    @Autowired
    private CommentService commentService;

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

}
