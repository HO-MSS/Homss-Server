package com.homss.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homss.server.ServerApplicationTests;
import com.homss.server.dto.request.EditMemberProfileRequest;
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

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    @DisplayName("사용자 프로필 설정")
    void edimtMemberProfile_test() throws Exception {
        // given
        String nickname = "nickname";
        String profileImage = "profile";
        String baekjoonId = "baek";
        EditMemberProfileRequest request = new EditMemberProfileRequest(nickname, profileImage, baekjoonId);
        Member newMember = Member.create(1L);
        memberMapper.save(newMember);

        // when & then
        mockMvc.perform(post("/api/member/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
