package com.homss.server.common.oauth;
import com.homss.server.ServerApplicationTests;
import com.homss.server.common.exception.ApplicationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class KakaoClientTest extends ServerApplicationTests {

    private static final String KAKAO_API_URL = "https://kapi.kakao.com/v2/user/me";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private KakaoClient kakaoClient;

    @Test
    @DisplayName("카카오 엑세스 토큰으로 사용자의 소셜 아이디를 얻는다.")
    void getMemberSocialId_test() {
        // given
        Long EXPECTED_SOCIAL_ID = 123456789L;

        ResponseEntity<String> responseEntity = mock(ResponseEntity.class);
        String jsonResponse = "{\"id\":\"" + EXPECTED_SOCIAL_ID + "\"}";

        when(responseEntity.getBody()).thenReturn(jsonResponse);
        when(restTemplate.exchange(eq(KAKAO_API_URL), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        // when
        Long socialId = kakaoClient.getMemberSocialId("token");

        // then
        Assertions.assertThat(socialId).isEqualTo(EXPECTED_SOCIAL_ID);
    }

    @Test
    @DisplayName("카카오 엑세스 토큰에 문제가 있으면 예외가 발생한다.")
    void getMemberSocialId_throw_ApplicationException_with_token_test() {
        // given
        String INVALID_TOKEN = "token";

        when(restTemplate.exchange(eq(KAKAO_API_URL), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException());

        // when & then
        Assertions.assertThatThrownBy(() -> kakaoClient.getMemberSocialId(INVALID_TOKEN))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    @DisplayName("사용자 정보에 id 필드가 없으면 예외가 발생한다.")
    void getMemberSocialId_throw_ApplicationException_with_id_test() {
        // given
        String INVALID_FIELD_NAME = "ID";

        ResponseEntity<String> responseEntity = mock(ResponseEntity.class);
        String jsonResponse = "{\""+INVALID_FIELD_NAME+"\":\"" + 123456789L + "\"}";

        when(responseEntity.getBody()).thenReturn(jsonResponse);
        when(restTemplate.exchange(eq(KAKAO_API_URL), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        // when & then
        Assertions.assertThatThrownBy(() -> kakaoClient.getMemberSocialId("token"))
                .isInstanceOf(ApplicationException.class);
    }
}

