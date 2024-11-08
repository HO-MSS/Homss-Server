package com.homss.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homss.server.ServerApplicationTests;
import com.homss.server.common.jwt.JwtProvider;
import com.homss.server.dto.request.CommentRequest;
import com.homss.server.mapper.BoardMapper;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.member.Member;
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
public class CommentControllerTest extends ServerApplicationTests {

    private static final String ACCESS_TOKEN = "Bearer AccessToken";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtProvider jwtProvider;

    @AfterEach
    void clean() {
        boardMapper.deleteAll();
        memberMapper.deleteAll();
    }


    @Test
    @DisplayName("댓글 등록")
    void checkNicknameDuplicate_test() throws Exception {
        // given
        String nickname = "nickname";
        Member member = Member.of(1L, nickname, "profile");
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        memberMapper.save(member);
        boardMapper.save(board);
        CommentRequest request = new CommentRequest("content");

        when(jwtProvider.validateToken(any(String.class))).thenReturn(true);
        when(jwtProvider.getMemberId(any(String.class))).thenReturn(member.getMemberId());

        // when & then
        mockMvc.perform(post("/api/comment/{boardId}", board.getBoardId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", ACCESS_TOKEN)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

}
