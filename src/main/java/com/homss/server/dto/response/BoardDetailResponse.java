package com.homss.server.dto.response;

import com.homss.server.model.board.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardDetailResponse {
    private Long boardId;
    private String title;
    private String content;
    private BoardType boardType;
    private Long viewNum;
    private LocalDateTime createdAt;

    private Long likeNum;
    private Boolean likeStatus;

    private Long memberId;
    private String memberProfileImage;
    private String memberNickname;
}
