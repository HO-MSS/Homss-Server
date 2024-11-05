package com.homss.server.model.board;

import com.homss.server.common.modle.TimeModel;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Alias("board")
public class Board extends TimeModel {

    private Long boardId;
    private Long memberId;
    private String title;
    private String content;
    private Long viewNum;
    private BoardType boardType;
    private BoardStatus boardStatus;

    public static Board of(Long memberId, BoardType boardType, String title, String content) {
        return Board.builder()
                .memberId(memberId)
                .boardType(boardType)
                .title(title)
                .content(content)
                .build();
    }

}
