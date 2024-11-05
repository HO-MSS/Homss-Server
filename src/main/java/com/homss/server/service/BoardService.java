package com.homss.server.service;

import com.homss.server.common.exception.ApplicationException;
import com.homss.server.common.exception.ExceptionCode;
import com.homss.server.dto.request.BoardRequest;
import com.homss.server.dto.response.BoardSaveResponse;
import com.homss.server.mapper.BoardMapper;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.board.Board;
import com.homss.server.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.homss.server.common.exception.ExceptionCode.MEMBER_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final MemberMapper memberMapper;

    @Transactional
    public BoardSaveResponse saveBoard(Long memberId, BoardRequest request) {
        Member member = memberMapper.findById(memberId)
                .orElseThrow(() -> ApplicationException.create(MEMBER_NOT_FOUND_ERROR));

        Board board = Board.of(member.getMemberId(), request.boardType(), request.title(), request.content());
        boardMapper.save(board);
        return BoardSaveResponse.from(board);
    }

}
