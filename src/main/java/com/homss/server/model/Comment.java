package com.homss.server.model;

import com.homss.server.common.modle.TimeModel;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("comment")
public class Comment extends TimeModel {

    private Long commentId;
    private Long memberId;
    private Long boardId;
    private Long parentId;
    private String content;

    public static Comment of(Long memberId, Long boardId, String content, Long parentId) {
        return Comment.builder()
                .memberId(memberId)
                .boardId(boardId)
                .content(content)
                .parentId(parentId)
                .build();
    }

}
