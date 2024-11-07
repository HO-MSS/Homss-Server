package com.homss.server.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record BoardListResponse(Integer pageNumber, Boolean hasNext, List<BoardSimpleResponse> content) {

    public static BoardListResponse from(Page<BoardSimpleResponse> boardPage) {
        return new BoardListResponse(boardPage.getNumber(), boardPage.hasNext(), boardPage.getContent());
    }

}
