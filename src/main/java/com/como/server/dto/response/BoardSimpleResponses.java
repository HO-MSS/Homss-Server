package com.como.server.dto.response;

import com.como.server.model.Board;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardSimpleResponses {

    public static BoardSimpleResponses from(Board board) {
        return BoardSimpleResponses.builder()
                .build();
    }
}
