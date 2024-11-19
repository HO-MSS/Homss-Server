package com.homss.server.service;

import com.homss.server.ServerApplicationTests;
import com.homss.server.common.exception.ApplicationException;
import com.homss.server.common.exception.ExceptionCode;
import com.homss.server.dto.request.BoardRequest;
import com.homss.server.dto.response.BoardDetailResponse;
import com.homss.server.dto.response.BoardListResponse;
import com.homss.server.dto.response.BoardIdResponse;
import com.homss.server.mapper.BoardLikeMapper;
import com.homss.server.mapper.BoardMapper;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardLike;
import com.homss.server.model.board.BoardStatus;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BoardServiceTest extends ServerApplicationTests {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private BoardLikeMapper boardLikeMapper;

    @Autowired
    private MemberMapper memberMapper;

    @AfterEach
    void clean() {
        boardLikeMapper.deleteAll();
        boardMapper.deleteAll();
        memberMapper.deleteAll();
    }

    @Test
    @DisplayName("게시글 등록")
    void saveBoard_test() {
        // given
        BoardRequest request = new BoardRequest(BoardType.NOTICE, "title", "content");
        Member member = Member.create(1L);
        memberMapper.save(member);

        // when
        BoardIdResponse response = boardService.saveBoard(member.getMemberId(), request);

        // then
        List<Board> boards = boardMapper.findAll();
        assertThat(boards.size()).isEqualTo(1);
        assertThat(response.boardId()).isEqualTo(boards.get(0).getBoardId());

    }

    @Test
    @DisplayName("게시글 목록 조회")
    void findAllBoardWithType_test() {
        // given
        BoardType boardType = BoardType.NOTICE;
        BoardType anotherType = BoardType.TEAM;
        Member member = Member.create(1L);
        memberMapper.save(member);
        boardMapper.save(Board.of(member.getMemberId(), boardType, "title1", "content"));
        boardMapper.save(Board.of(member.getMemberId(), anotherType, "title2", "content"));

        PageRequest pageable = PageRequest.of(0, 2);

        // when
        BoardListResponse response = boardService.findAllBoardWithType(boardType, null, pageable);

        // then
        assertThat(response.content().size()).isEqualTo(1);

    }

    @Test
    @DisplayName("게시글 목록 조회")
    void findAllBoardWithType_pagination_test() {
        // given
        BoardType boardType = BoardType.NOTICE;
        Member member = Member.create(1L);
        memberMapper.save(member);
        boardMapper.save(Board.of(member.getMemberId(), boardType, "title1", "content"));
        boardMapper.save(Board.of(member.getMemberId(), boardType, "title2", "content"));
        boardMapper.save(Board.of(member.getMemberId(), boardType, "title3", "content"));

        PageRequest pageable = PageRequest.of(0, 2);

        // when
        BoardListResponse response = boardService.findAllBoardWithType(boardType, null, pageable);

        // then
        assertThat(response.content().size()).isEqualTo(2);
        assertThat(response.pageNumber()).isEqualTo(0);
        assertThat(response.hasNext()).isTrue();

    }

    @Test
    @DisplayName("게시글 상세 조회")
    void findById_test() {
        // given
        String title = "title";
        String content = "content";
        BoardType boardType = BoardType.NOTICE;
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), boardType, title, content);
        boardMapper.save(board);

        // when
        BoardDetailResponse boardDetail = boardService.findById(null, board.getBoardId());

        // then
        assertThat(boardDetail.getBoardId()).isEqualTo(board.getBoardId());
        assertThat(boardDetail.getViewNum()).isEqualTo(1);

    }

    @Test
    @DisplayName("게시글 좋아요가 없으면 등록")
    void postBoardLike_save_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);

        // when
        boardService.postBoardLike(board.getBoardId(), member.getMemberId());

        // then
        assertThat(boardLikeMapper.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 좋아요가 있으면 삭제")
    void postBoardLike_delete_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        BoardLike newBoardLike = BoardLike.of(board.getBoardId(), member.getMemberId());
        boardLikeMapper.save(newBoardLike);

        // when
        boardService.postBoardLike(board.getBoardId(), member.getMemberId());

        // then
        assertThat(boardLikeMapper.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시글 삭제 조회")
    void deleteById_test() {
        // given
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board newBoard = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(newBoard);

        // when
        boardService.deleteById(member.getMemberId(), newBoard.getBoardId());

        // then
        Board board = boardMapper.findById(newBoard.getBoardId()).orElse(null);
        assertThat(board).isNotNull();
        assertThat(board.getBoardStatus()).isEqualTo(BoardStatus.DELETE);
    }

    @Test
    @DisplayName("게시글 삭제 시, 존재하지 않는 게시글일 경우 예외 발생")
    void deleteById_BoardNotFoundException_test() {
        // given
        Long NOT_EXIST_BOARD_ID = -1L;
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board newBoard = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(newBoard);

        // when & then
        assertThatThrownBy(() -> boardService.deleteById(member.getMemberId(), NOT_EXIST_BOARD_ID))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(ExceptionCode.BOARD_NOT_FOUND_ERROR.getMessage());
    }

    @Test
    @DisplayName("게시글 삭제 시, 작성자와 요청자가 일치하지 않을 경우 예외 발생")
    void deleteById_NotMatchBoardAuthorException_test() {
        // given
        Long NOT_AUTHOR_MEMBER_ID = -1L;
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board newBoard = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(newBoard);

        // when & then
        assertThatThrownBy(() -> boardService.deleteById(NOT_AUTHOR_MEMBER_ID, newBoard.getBoardId()))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(ExceptionCode.NOT_BOARD_AUTHOR_ERROR.getMessage());
    }

    @Test
    @DisplayName("게시글 수정")
    void editBoard_test() {
        // given
        String newTitle = "newTitle";
        String newContent = "newContent";
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(board);

        BoardRequest request = new BoardRequest(BoardType.QNA, newTitle, newContent);

        // when
        BoardIdResponse response = boardService.editBoard(member.getMemberId(), board.getBoardId(), request);

        // then
        assertThat(response.boardId()).isEqualTo(board.getBoardId());
    }

    @Test
    @DisplayName("게시글 수정 시, 존재하지 않는 게시글일 경우 예외 발생")
    void editBoard_BoardNotFoundException_test() {
        // given
        Long NOT_EXIST_BOARD_ID = -1L;
        String newTitle = "newTitle";
        String newContent = "newContent";
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(board);

        BoardRequest request = new BoardRequest(BoardType.QNA, newTitle, newContent);

        // when & then
        assertThatThrownBy(() -> boardService.editBoard(member.getMemberId(), NOT_EXIST_BOARD_ID, request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(ExceptionCode.BOARD_NOT_FOUND_ERROR.getMessage());
    }

    @Test
    @DisplayName("게시글 수정 시, 작성자와 요청자가 일치하지 않을 경우 예외 발생")
    void editBoard_NotMatchBoardAuthorException_test() {
        // given
        Long NOT_AUTHOR_MEMBER_ID = -1L;
        String newTitle = "newTitle";
        String newContent = "newContent";
        Member member = Member.create(1L);
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(board);

        BoardRequest request = new BoardRequest(BoardType.QNA, newTitle, newContent);

        // when & then
        assertThatThrownBy(() -> boardService.editBoard(NOT_AUTHOR_MEMBER_ID, board.getBoardId(), request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(ExceptionCode.NOT_BOARD_AUTHOR_ERROR.getMessage());
    }

}
