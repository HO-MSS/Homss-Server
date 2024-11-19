package com.homss.server.mapper;

import com.homss.server.ServerApplicationTests;
import com.homss.server.dto.response.BoardDetailResponse;
import com.homss.server.dto.response.BoardSimpleResponse;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardStatus;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(boardMapper.findAll().size()).isEqualTo(1);
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
        assertThat(boards.size()).isEqualTo(2);
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
        assertThat(boardMapper.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시글 타입에 따라 모두 조회")
    void findAllByType_test() {
        // given
        Member member1 = Member.create(1L);
        Member member2 = Member.create(2L);
        memberMapper.save(member1);
        memberMapper.save(member2);
        boardMapper.save(Board.of(member1.getMemberId(), BoardType.NOTICE, "title1", "content"));
        boardMapper.save(Board.of(member2.getMemberId(), BoardType.QNA, "title2", "content"));

        // when
        List<BoardSimpleResponse> boards = boardMapper.findAllByType(BoardType.NOTICE, null, 0L, 10);

        // then
        assertThat(boards.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 타입에 따라 페이지를 모두 조회")
    void findAllByType_Pageable_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        for (int i=0; i<3; i++) {
            boardMapper.save(Board.of(member.getMemberId(), BoardType.NOTICE, "title" + i, "content"));
        }

        // when
        List<BoardSimpleResponse> boards = boardMapper.findAllByType(BoardType.NOTICE, null, 0L, 2);

        // then
        assertThat(boards.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 타입에 따라 게시글 수를 조회")
    void countByType_test() {
        // given
        int COUNT = 3;
        Member member = Member.create(1L);
        memberMapper.save(member);

            // Active Board
        for (int i=0; i<COUNT; i++) {
            boardMapper.save(Board.of(member.getMemberId(), BoardType.NOTICE, "title" + i, "content"));
        }

            // Delete Board
        Board deletedBoard = Board.of(member.getMemberId(), BoardType.NOTICE, "del title", "content");
        boardMapper.save(deletedBoard);
        boardMapper.changeStatusById(deletedBoard.getBoardId(), BoardStatus.DELETE);

        // when
        Long count = boardMapper.countByType(BoardType.NOTICE, null);

        // then
        assertThat(count).isEqualTo(COUNT);
    }

    @Test
    @DisplayName("게시글 모두 조회 시 타입이 없으면 모든 타입 게시글을 조회")
    void findAllByType_WithoutType_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);

            // Active Board
        boardMapper.save(Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content"));
        boardMapper.save(Board.of(member.getMemberId(), BoardType.QNA, "title2", "content"));

            // Delete Board
        Board deletedBoard = Board.of(member.getMemberId(), BoardType.NOTICE, "del title", "content");
        boardMapper.save(deletedBoard);
        boardMapper.changeStatusById(deletedBoard.getBoardId(), BoardStatus.DELETE);

        // when
        List<BoardSimpleResponse> boards = boardMapper.findAllByType(null, null, 0L, 10);

        // then
        assertThat(boards.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 상세조회")
    void findDetailById_test() {
        // given
        String title = "title";
        String content = "content";
        BoardType boardType = BoardType.NOTICE;
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), boardType, title, content);
        boardMapper.save(board);

        // when
        BoardDetailResponse boardDetail = boardMapper.findDetailById(null, board.getBoardId());

        // then
        assertThat(boardDetail.getBoardId()).isEqualTo(board.getBoardId());
        assertThat(boardDetail.getTitle()).isEqualTo(title);
        assertThat(boardDetail.getContent()).isEqualTo(content);
        assertThat(boardDetail.getBoardType()).isEqualTo(boardType);
        assertThat(boardDetail.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(boardDetail.getMemberNickname()).isEqualTo(member.getNickname());

    }

    @Test
    @DisplayName("게시글 검색어 조회")
    void findAll_WithKeyword_test() {
        // given
        String keyword = "title";
        Member member1 = Member.create(1L);
        Member member2 = Member.create(2L);
        memberMapper.save(member1);
        memberMapper.save(member2);
        boardMapper.save(Board.of(member1.getMemberId(), BoardType.NOTICE, "title1", "content"));
        boardMapper.save(Board.of(member2.getMemberId(), BoardType.NOTICE, "Yes title1", "content"));
        boardMapper.save(Board.of(member2.getMemberId(), BoardType.QNA, "Yes title", "content"));
        boardMapper.save(Board.of(member2.getMemberId(), BoardType.QNA, "Yes", "content"));

        // when
        List<BoardSimpleResponse> allBoards = boardMapper.findAllByType(null, keyword, 0L, 10);
        List<BoardSimpleResponse> noticeBoards = boardMapper.findAllByType(BoardType.NOTICE, keyword, 0L, 10);

        // then
        assertThat(allBoards.size()).isEqualTo(3);
        assertThat(noticeBoards.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 아이디로 조회")
    void findById_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board newBoard = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(newBoard);

        // when
        Board board = boardMapper.findById(newBoard.getBoardId()).orElse(null);

        // then
        assertThat(board).isNotNull();
        assertThat(board.getBoardId()).isEqualTo(newBoard.getBoardId());
    }

    @Test
    @DisplayName("게시글 상태 변경")
    void changeStatusById_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board newBoard = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(newBoard);

        // when
        boardMapper.changeStatusById(newBoard.getBoardId(), BoardStatus.DELETE);

        // then
        Board board = boardMapper.findById(newBoard.getBoardId()).orElse(null);
        assertThat(board).isNotNull();
        assertThat(board.getBoardStatus()).isEqualTo(BoardStatus.DELETE);
    }

    @Test
    @DisplayName("게시글 수정")
    void editById_test() {
        // given
        String newTitle = "newTitle";
        String newContent = "newContent";
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board newBoard = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(newBoard);

        // when
        boardMapper.editById(newBoard.getBoardId(), BoardType.QNA, newTitle, newContent);

        // then
        Board board = boardMapper.findById(newBoard.getBoardId()).orElse(null);
        assertThat(board).isNotNull();
        assertThat(board.getBoardType()).isEqualTo(BoardType.QNA);
        assertThat(board.getTitle()).isEqualTo(newTitle);
        assertThat(board.getContent()).isEqualTo(newContent);
    }

}
