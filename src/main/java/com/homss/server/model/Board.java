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
    private String type;
    private String title;
    private String content;

    public static Board of(Long memberId, String type, String title, String content) {
        return Board.builder()
                .memberId(memberId)
                .type(type)
                .title(title)
                .content(content)
                .build();
    }

}
