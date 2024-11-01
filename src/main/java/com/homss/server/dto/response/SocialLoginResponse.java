package com.homss.server.dto.response;

import com.homss.server.dto.etc.MemberJwtTokens;

public record SocialLoginResponse(String accessToken, String refreshToken) {
    public static SocialLoginResponse from(MemberJwtTokens memberJwtTokens) {
        return new SocialLoginResponse(memberJwtTokens.accessToken(), memberJwtTokens.refreshToken());
    }
}
