package com.homss.server.dto.request;

import com.homss.server.model.board.BoardType;

public record BoardRequest(BoardType boardType, String title, String content) {
}
