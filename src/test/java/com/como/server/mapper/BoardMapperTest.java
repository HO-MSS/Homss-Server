package com.como.server.mapper;

import com.como.server.model.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
public class BoardMapperTest {

    @Autowired
    private BoardMapper boardMapper;

    @DisplayName("게시글 저장")
    @Test
    void save_test() {
        //given
        Board board = Board.of(1L, "QNA", "title", "content");

        //when
        boardMapper.save(board);

        //then
        Assertions.assertThat(board.getBoardId()).isNotNull();
    }

//    @DisplayName("타입에 맞는 게시글을 모두 조회")
//    @Test
//    void findAllByType_test() {
//        //given
//
//
//        //when
//
//        //then
//    }

}
