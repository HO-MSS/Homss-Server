package com.homss.server.dto.response;

import com.homss.server.model.board.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardSimpleResponse{
    private Long boardId;
    private String title;
    private BoardType boardType;
    private Long memberId;
    private String memberProfileImage;
    private String memberNickname;
    private Long viewNum;
    private Long likeNum;
    private Long commentNum;
    private LocalDateTime createdAt;
}
