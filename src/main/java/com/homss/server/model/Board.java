package com.homss.server.model;

import com.homss.server.common.modle.TimeModel;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("board")
public class Board extends TimeModel {

    private Long boardId;
    private Long memberId;
    private String content;
    private String boardType;
    private String title;
    private BoardStatus boardStatus;

    public static Board of(Long memberId, String boardType, String title, String content) {
        return Board.builder()
                .memberId(memberId)
                .boardType(boardType)
                .title(title)
                .content(content)
                .build();
    }

}
