package com.homss.server.service;

import com.homss.server.ServerApplicationTests;
import com.homss.server.dto.request.BoardRequest;
import com.homss.server.dto.response.BoardDetailResponse;
import com.homss.server.dto.response.BoardListResponse;
import com.homss.server.dto.response.BoardSaveResponse;
import com.homss.server.mapper.BoardLikeMapper;
import com.homss.server.mapper.BoardMapper;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardLike;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        BoardSaveResponse response = boardService.saveBoard(member.getMemberId(), request);

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

}
