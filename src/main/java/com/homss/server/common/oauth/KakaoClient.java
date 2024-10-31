package com.homss.server.common.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homss.server.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.homss.server.common.exception.ExceptionCode.ID_FILED_NOT_FOUND_ERROR;
import static com.homss.server.common.exception.ExceptionCode.INVALIDATE_TOKEN_ERROR;

@Component
@RequiredArgsConstructor
public class KakaoClient {

    private static final String KAKAO_API_URL = "https://kapi.kakao.com/v2/user/me";
    private final RestTemplate restTemplate;

    public Long getMemberSocialId(String socialAccessToken) {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + socialAccessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoMemberInfoRequest = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_API_URL,
                    HttpMethod.POST,
                    kakaoMemberInfoRequest,
                    String.class
            );

            return convertToSocialId(response);
        } catch (NullPointerException exception) {
            throw ApplicationException.create(ID_FILED_NOT_FOUND_ERROR);
        } catch (Exception exception) {
            throw ApplicationException.create(INVALIDATE_TOKEN_ERROR);
        }
    }

    private Long convertToSocialId(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = response.getBody();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return Long.parseLong(jsonNode.get("id").asText());
    }
}
