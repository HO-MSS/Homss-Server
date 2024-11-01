package com.homss.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homss.server.ServerApplicationTests;
import com.homss.server.common.jwt.JwtProvider;
import com.homss.server.dto.request.SocialLoginRequest;
import com.homss.server.dto.response.SocialLoginResponse;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.member.Member;
import com.homss.server.service.AuthService;
import org.junit.jupiter.api.AfterEach;
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

    private static final String ACCESS_TOKEN = "Bearer AccessToken";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private MemberMapper memberMapper;

    @AfterEach
    void clean() {
        memberMapper.deleteAll();
    }


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
    @Test
    @DisplayName("로그아웃")
    void logout_test() throws Exception {
        // given
        String nickname = "nickname";
        Member newMember = Member.of(1L, nickname, "profile");
        memberMapper.save(newMember);

        when(jwtProvider.validateToken(any(String.class))).thenReturn(true);
        when(jwtProvider.getMemberId(any(String.class))).thenReturn(newMember.getMemberId());

        // when & then
        mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", ACCESS_TOKEN))
                .andExpect(status().isOk());
    }

}

