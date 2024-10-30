package com.como.server.model;

import com.como.server.common.modle.TimeModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("comment_like")
public class CommentLike extends TimeModel {

    private Long commentLikeId;
    private Long commentId;
    private Long memberId;

}
