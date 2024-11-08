package com.homss.server.service;

import com.homss.server.common.exception.ApplicationException;
import com.homss.server.common.utils.PageConverter;
import com.homss.server.dto.request.BoardRequest;
import com.homss.server.dto.response.BoardDetailResponse;
import com.homss.server.dto.response.BoardListResponse;
import com.homss.server.dto.response.BoardSaveResponse;
import com.homss.server.dto.response.BoardSimpleResponse;
import com.homss.server.mapper.BoardMapper;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.homss.server.common.exception.ExceptionCode.MEMBER_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
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
    public BoardListResponse findAllBoardWithType(BoardType boardType, Pageable pageable) {
        List<BoardSimpleResponse> boards = boardMapper.findAllByType(boardType, pageable.getOffset(), pageable.getPageSize());
        Long boardCount = boardMapper.countByType(boardType);
        Page<BoardSimpleResponse> boardPage = boardPageConverter.toPage(boards, boardCount, pageable);

        return BoardListResponse.from(boardPage);
    }

    @Transactional
    public BoardDetailResponse findById(Long memberId, Long boardId) {
        boardMapper.increaseViewNum(boardId);
        return boardMapper.findDetailById(memberId, boardId);
    }
}
