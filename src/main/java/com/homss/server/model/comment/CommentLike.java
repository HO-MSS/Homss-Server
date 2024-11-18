package com.homss.server.model.comment;

import com.homss.server.common.modle.TimeModel;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("comment_like")
public class CommentLike extends TimeModel {

    private Long commentLikeId;
    private Long commentId;
    private Long memberId;

    public static CommentLike of(Long commentId, Long memberId) {
        return CommentLike.builder()
                .commentId(commentId)
                .memberId(memberId)
                .build();
    }

}
