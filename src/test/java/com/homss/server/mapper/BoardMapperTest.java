package com.homss.server.mapper;

import com.homss.server.ServerApplicationTests;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BoardMapperTest extends ServerApplicationTests {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private MemberMapper memberMapper;


    @AfterEach
    void clean() {
        boardMapper.deleteAll();
        memberMapper.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장")
    void save_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");

        // when
        boardMapper.save(board);

        // then
        Assertions.assertThat(boardMapper.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 모두 조회")
    void findAll_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        boardMapper.save(Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content"));
        boardMapper.save(Board.of(member.getMemberId(), BoardType.NOTICE, "title2", "content"));

        // when
        List<Board> boards = boardMapper.findAll();

        // then
        Assertions.assertThat(boards.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 모두 삭제")
    void deleteAll_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        boardMapper.save(Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content"));
        boardMapper.save(Board.of(member.getMemberId(), BoardType.NOTICE, "title2", "content"));

        // when
        boardMapper.deleteAll();

        // then
        Assertions.assertThat(boardMapper.findAll().size()).isEqualTo(0);
    }

}
