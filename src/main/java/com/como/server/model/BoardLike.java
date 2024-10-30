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
@Alias("board_like")
public class BoardLike extends TimeModel {

    private Long boardLikeId;
    private Long boardId;
    private Long memberId;

}
