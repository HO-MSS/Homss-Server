package com.homss.server.model;

import com.homss.server.common.modle.TimeModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("comment")
public class Comment extends TimeModel {

    private Long commentId;
    private Long boardId;
    private Long parentId;
    private String content;

}
