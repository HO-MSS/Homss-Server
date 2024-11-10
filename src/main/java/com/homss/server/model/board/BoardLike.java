package com.homss.server.model.board;

import com.homss.server.common.modle.TimeModel;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("board_like")
public class BoardLike extends TimeModel {

    private Long boardLikeId;
    private Long boardId;
    private Long memberId;

    public static BoardLike of(Long boardId, Long memberId) {
        return BoardLike.builder()
                .boardId(boardId)
                .memberId(memberId)
                .build();
    }

}
