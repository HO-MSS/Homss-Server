package com.homss.server.controller;

import com.homss.server.ServerApplicationTests;
import com.homss.server.dto.request.SocialLoginRequest;
import com.homss.server.dto.response.SocialLoginResponse;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class MemberControllerTest extends ServerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberMapper memberMapper;

    @AfterEach
    void clean() {
        memberMapper.deleteAll();
    }


    @Test
    @DisplayName("닉네임 중복체크 확인")
    void checkNicknameDuplicate_test() throws Exception {
        // given
        String nickname = "nickname";
        memberMapper.save(Member.of(1L, nickname, "profile"));

        // when & then
        mockMvc.perform(get("/api/member/duplicate/{nickname}", nickname)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isDuplicate").value(true));
    }
}
