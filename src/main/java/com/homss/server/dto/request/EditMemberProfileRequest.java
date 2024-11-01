package com.homss.server.dto.request;

import jakarta.validation.constraints.NotNull;

public record EditMemberProfileRequest(@NotNull String nickname,
                                       @NotNull String profileImage,
                                       @NotNull  String baekjoonId) {
}
