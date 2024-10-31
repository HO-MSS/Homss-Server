package com.homss.server.dto.request;


import jakarta.validation.constraints.NotNull;

public record SocialLoginRequest(@NotNull String socialAccessToken) {
}
