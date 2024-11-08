package com.homss.server.dto.request;

public record CommentRequest(String content, Long parentId) {
}
