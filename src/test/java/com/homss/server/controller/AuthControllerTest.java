package com.homss.server.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homss.server.ServerApplicationTests;
import com.homss.server.dto.request.SocialLoginRequest;
import com.homss.server.dto.response.SocialLoginResponse;
import com.homss.server.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
class AuthControllerTest extends ServerApplicationTests{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("카카오 엑세스 토큰으로 로그인을 할 수 있다.")
    void login_test() throws Exception {
        // given
        SocialLoginRequest request = new SocialLoginRequest("SOCIAL_ACCESS_TOKEN");
        SocialLoginResponse response = new SocialLoginResponse("ACCESS_TOKEN", "REFRESH_TOKEN");

        Mockito.when(authService.login(Mockito.any(SocialLoginRequest.class)))
                .thenReturn(response);

        // when & then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("ACCESS_TOKEN"))
                .andExpect(jsonPath("$.refreshToken").value("REFRESH_TOKEN"));
    }
}

