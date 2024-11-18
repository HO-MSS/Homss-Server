package com.homss.server.dto.response;

import com.homss.server.model.comment.CommentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponse {
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

    private List<SubCommentResponse> subComments;

}
