package com.homss.server.mapper;

import com.homss.server.ServerApplicationTests;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardLike;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardLikeMapperTest extends ServerApplicationTests {

    @Autowired
    private BoardLikeMapper boardLikeMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private MemberMapper memberMapper;


    @AfterEach
    void clean() {
        boardLikeMapper.deleteAll();
        boardMapper.deleteAll();
        memberMapper.deleteAll();
    }

    @Test
    @DisplayName("게시글 좋아요 모두 삭제")
    void deleteAll_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);

        BoardLike newBoardLike = BoardLike.of(board.getBoardId(), member.getMemberId());
        boardLikeMapper.save(newBoardLike);

        // when
        boardLikeMapper.deleteAll();

        // then
        assertThat(boardLikeMapper.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시글 좋아요 모두 조회")
    void findAll_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board1 = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        Board board2 = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board1);
        boardMapper.save(board2);

        BoardLike newBoardLike1 = BoardLike.of(board1.getBoardId(), member.getMemberId());
        BoardLike newBoardLike2 = BoardLike.of(board2.getBoardId(), member.getMemberId());

        boardLikeMapper.save(newBoardLike1);
        boardLikeMapper.save(newBoardLike2);

        // when
        List<BoardLike> allBoardLike = boardLikeMapper.findAll();

        // then
        assertThat(allBoardLike.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 좋아요 저장")
    void save_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);

        BoardLike newBoardLike = BoardLike.of(board.getBoardId(), member.getMemberId());

        // when
        boardLikeMapper.save(newBoardLike);

        // then
        assertThat(boardLikeMapper.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 좋아요 상태 조회")
    void findBoardLikeStatus_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);

        BoardLike newBoardLike = BoardLike.of(board.getBoardId(), member.getMemberId());
        boardLikeMapper.save(newBoardLike);

        // when
        Boolean likeStatus = boardLikeMapper.findLikeStatus(board.getBoardId(), member.getMemberId());

        // then
        assertThat(likeStatus).isTrue();
    }

    @Test
    @DisplayName("게시글 좋아요 삭제")
    void deleteLike_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board1 = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        Board board2 = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board1);
        boardMapper.save(board2);

        BoardLike newBoardLike1 = BoardLike.of(board1.getBoardId(), member.getMemberId());
        BoardLike newBoardLike2 = BoardLike.of(board2.getBoardId(), member.getMemberId());
        boardLikeMapper.save(newBoardLike1);
        boardLikeMapper.save(newBoardLike2);

        // when
        boardLikeMapper.deleteLike(board1.getBoardId(), member.getMemberId());

        // then
        assertThat(boardLikeMapper.findAll().size()).isEqualTo(1);
    }

}
