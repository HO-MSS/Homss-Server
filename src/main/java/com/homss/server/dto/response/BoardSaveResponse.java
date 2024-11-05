package com.homss.server.dto.response;

import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardStatus;
import com.homss.server.model.board.BoardType;

import java.time.LocalDateTime;

public record BoardSaveResponse(Long boardId) {

    public static BoardSaveResponse from(Board board) {
        return new BoardSaveResponse(board.getBoardId());
    }

}
