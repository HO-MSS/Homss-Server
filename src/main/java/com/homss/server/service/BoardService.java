package com.homss.server.service;

import com.homss.server.common.exception.ApplicationException;
import com.homss.server.common.utils.PageConverter;
import com.homss.server.dto.request.BoardRequest;
import com.homss.server.dto.response.*;
import com.homss.server.mapper.BoardLikeMapper;
import com.homss.server.mapper.BoardMapper;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardLike;
import com.homss.server.model.board.BoardStatus;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.homss.server.common.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final BoardLikeMapper boardLikeMapper;
    private final MemberMapper memberMapper;

    private final PageConverter<BoardSimpleResponse> boardPageConverter;

    @Transactional
    public BoardSaveResponse saveBoard(Long memberId, BoardRequest request) {
        Member member = memberMapper.findById(memberId)
                .orElseThrow(() -> ApplicationException.create(MEMBER_NOT_FOUND_ERROR));

        Board board = Board.of(member.getMemberId(), request.boardType(), request.title(), request.content());
        boardMapper.save(board);
        return BoardSaveResponse.from(board);
    }

    @Transactional(readOnly = true)
    public BoardListResponse findAllBoardWithType(BoardType boardType, String keyword, Pageable pageable) {
        List<BoardSimpleResponse> boards = boardMapper.findAllByType(boardType, keyword,
                pageable.getOffset(), pageable.getPageSize());
        Long boardCount = boardMapper.countByType(boardType, keyword);
        Page<BoardSimpleResponse> boardPage = boardPageConverter.toPage(boards, boardCount, pageable);

        return BoardListResponse.from(boardPage);
    }

    @Transactional
    public BoardDetailResponse findById(Long memberId, Long boardId) {
        boardMapper.increaseViewNum(boardId);
        return boardMapper.findDetailById(memberId, boardId);
    }

    @Transactional
    public LikeResponse postBoardLike(Long boardId, Long memberId) {
        Boolean likeStatus = boardLikeMapper.findLikeStatus(boardId, memberId);

        if (likeStatus) {
            boardLikeMapper.deleteLike(boardId, memberId);
        } else {
            BoardLike newBoardLike = BoardLike.of(boardId, memberId);
            boardLikeMapper.save(newBoardLike);
        }

        return new LikeResponse(!likeStatus);
    }

    @Transactional
    public void deleteById(Long memberId, Long boardId) {
        Board board = boardMapper.findById(boardId)
                .orElseThrow(() -> ApplicationException.create(BOARD_NOT_FOUND_ERROR));

        if (!board.getMemberId().equals(memberId)) {
            throw ApplicationException.create(NOT_BOARD_AUTHOR_ERROR);
        }

        boardMapper.changeStatus(boardId, BoardStatus.DELETE);
    }
}
