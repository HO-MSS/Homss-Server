package com.homss.server.dto.response;

import com.homss.server.model.board.Board;

public record BoardIdResponse(Long boardId) {

    public static BoardIdResponse from(Board board) {
        return new BoardIdResponse(board.getBoardId());
    }

}
