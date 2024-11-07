package com.homss.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homss.server.ServerApplicationTests;
import com.homss.server.common.jwt.JwtProvider;
import com.homss.server.dto.request.BoardRequest;
import com.homss.server.dto.request.EditMemberProfileRequest;
import com.homss.server.mapper.BoardMapper;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.member.Member;
import com.homss.server.service.BoardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class BoardControllerTest extends ServerApplicationTests {

    private static final String ACCESS_TOKEN = "Bearer AccessToken";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private BoardMapper boardMapper;

    @MockBean
    private JwtProvider jwtProvider;

    @AfterEach
    void clean() {
        boardMapper.deleteAll();
        memberMapper.deleteAll();
    }


    @Test
    @DisplayName("게시물 등록")
    void saveBoard_test() throws Exception {
        // given
        BoardType boardType = BoardType.NOTICE;
        String title = "title";
        String content = "content";
        BoardRequest request = new BoardRequest(boardType, title, content);

        Member newMember = Member.create(1L);
        memberMapper.save(newMember);

        when(jwtProvider.validateToken(any(String.class))).thenReturn(true);
        when(jwtProvider.getMemberId(any(String.class))).thenReturn(newMember.getMemberId());

        // when & then
        mockMvc.perform(post("/api/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", ACCESS_TOKEN)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시물 모두 조회 with Type")
    void findAllBoardWithType_test() throws Exception {
        // given
        BoardType boardType = BoardType.NOTICE;
        int size = 10;
        int page = 0;

        // when & then
        mockMvc.perform(get("/api/board/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("type", boardType.toString())
                        .param("size", Integer.toString(size))
                        .param("page", Integer.toString(page)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시물 모두 조회 시 없는 Enum일 경우")
    void findAllBoardWithType_enum_test() throws Exception {
        // given
        String inValidEnum = "ENUM";
        int size = 10;
        int page = 0;

        // when & then
        mockMvc.perform(get("/api/board/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("type", inValidEnum)
                        .param("size", Integer.toString(size))
                        .param("page", Integer.toString(page)))
                .andExpect(status().is4xxClientError());
    }

}
