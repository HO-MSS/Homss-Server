package com.homss.server.common.jwt;

import com.homss.server.ServerApplicationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtProviderTest extends ServerApplicationTests {

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("엑세스 토큰 발급")
    void createAccessToken_test() {
        // given & when
        String accessToken = jwtProvider.createAccessToken(1L);

        // then
        assertThat(jwtProvider.getMemberId(accessToken)).isEqualTo(1L);
    }

    @Test
    @DisplayName("리프레쉬 토큰 발급")
    void createRefreshToken_test() {
        // given & when
        String refreshToken = jwtProvider.createRefreshToken(1L);

        // then
        assertThat(jwtProvider.getMemberId(refreshToken)).isEqualTo(1L);
    }
}
