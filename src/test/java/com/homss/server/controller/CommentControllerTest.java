package com.homss.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homss.server.ServerApplicationTests;
import com.homss.server.common.jwt.JwtProvider;
import com.homss.server.dto.request.CommentEditRequest;
import com.homss.server.dto.request.CommentRequest;
import com.homss.server.mapper.BoardMapper;
import com.homss.server.mapper.CommentLikeMapper;
import com.homss.server.mapper.CommentMapper;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardType;
import com.homss.server.model.comment.Comment;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private CommentMapper commentMapper;

    @Autowired
    private CommentLikeMapper commentLikeMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtProvider jwtProvider;

    @AfterEach
    void clean() {
        commentLikeMapper.deleteAll();
        commentMapper.deleteAll();
        boardMapper.deleteAll();
        memberMapper.deleteAll();
    }

    @Test
    @DisplayName("댓글 등록")
    void saveComment_test() throws Exception {
        // given
        String nickname = "nickname";
        Member member = Member.of(1L, nickname, "profile");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(board);
        CommentRequest request = new CommentRequest("content", null);

        when(jwtProvider.validateToken(any(String.class))).thenReturn(true);
        when(jwtProvider.getMemberId(any(String.class))).thenReturn(member.getMemberId());

        // when & then
        mockMvc.perform(post("/api/comment/{boardId}", board.getBoardId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", ACCESS_TOKEN)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment_test() throws Exception {
        // given
        String nickname = "nickname";
        Member member = Member.of(1L, nickname, "profile");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(board);
        Comment newComment = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(newComment);

        when(jwtProvider.validateToken(any(String.class))).thenReturn(true);
        when(jwtProvider.getMemberId(any(String.class))).thenReturn(member.getMemberId());

        // when & then
        mockMvc.perform(delete("/api/comment/{commentId}", newComment.getCommentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", ACCESS_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 수정")
    void editComment_test() throws Exception {
        // given
        String nickname = "nickname";
        Member member = Member.of(1L, nickname, "profile");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title1", "content");
        boardMapper.save(board);
        Comment newComment = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(newComment);

        CommentEditRequest request = new CommentEditRequest("edit content");

        when(jwtProvider.validateToken(any(String.class))).thenReturn(true);
        when(jwtProvider.getMemberId(any(String.class))).thenReturn(member.getMemberId());

        // when & then
        mockMvc.perform(put("/api/comment/{commentId}", newComment.getCommentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", ACCESS_TOKEN)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 좋아요")
    void postCommentLike_test() throws Exception {
        // given
        Member member = Member.of(1L, "member", "url");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment comment = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(comment);

        CommentEditRequest request = new CommentEditRequest("edit content");

        when(jwtProvider.validateToken(any(String.class))).thenReturn(true);
        when(jwtProvider.getMemberId(any(String.class))).thenReturn(member.getMemberId());

        // when & then
        mockMvc.perform(post("/api/comment/like/{commentId}", comment.getCommentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", ACCESS_TOKEN)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.likeStatus").value(true));
    }

    @Test
    @DisplayName("댓글 모두 조회")
    void getAllComment_test() throws Exception {
        // given
        Member member = Member.of(1L, "member", "url");
        memberMapper.save(member);
        Board board = Board.of(member.getMemberId(), BoardType.NOTICE, "title", "content");
        boardMapper.save(board);
        Comment newComment1 = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        Comment newComment2 = Comment.of(member.getMemberId(), board.getBoardId(), "content", null);
        commentMapper.save(newComment1);
        commentMapper.save(newComment2);
        Comment newComment3 = Comment.of(member.getMemberId(), board.getBoardId(), "content", newComment1.getCommentId());
        commentMapper.save(newComment3);

        // when & then
        mockMvc.perform(get("/api/comment/{boardId}", board.getBoardId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
