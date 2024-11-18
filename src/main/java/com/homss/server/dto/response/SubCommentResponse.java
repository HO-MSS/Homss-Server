package com.homss.server.dto.response;

import com.homss.server.model.comment.CommentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class SubCommentResponse {
    private Long commentId;

    private Long memberId;
    private String memberNickname;
    private String memberProfileImage;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CommentStatus commentStatus;

    private Long commentLikeNum;
    private Boolean commentLikeStatus;


}
