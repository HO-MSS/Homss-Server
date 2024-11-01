package com.homss.server.service;

import com.homss.server.ServerApplicationTests;
import com.homss.server.common.jwt.JwtProvider;
import com.homss.server.common.oauth.KakaoClient;
import com.homss.server.dto.request.SocialLoginRequest;
import com.homss.server.dto.response.SocialLoginResponse;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceTest extends ServerApplicationTests {

    private static final String KAKAO_API_URL = "https://kapi.kakao.com/v2/user/me";

    @Autowired
    private AuthService authService;

    @MockBean
    private KakaoClient kakaoClient;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @AfterEach
    void clean() {
        memberMapper.deleteAll();
    }


    @Test
    @DisplayName("카카오 엑세스토큰으로 로그인을 할 수 있다.")
    void login_test() {
        // given
        Long memberSocialId = 1L;
        SocialLoginRequest request = new SocialLoginRequest("SOCIAL_ACCESS_TOKEN");

        when(kakaoClient.getMemberSocialId(any( String.class))).thenReturn(memberSocialId);

        // when
        SocialLoginResponse response = authService.login(request);

        // then
        Long memberId = jwtProvider.getMemberId(response.accessToken());
        Member member = memberMapper.findById(memberId);
        Assertions.assertThat(member).isNotNull();
        Assertions.assertThat(member.getSocialId()).isEqualTo(memberSocialId);
    }
}
