package com.como.server.service;

import com.como.server.dto.response.BoardSimpleResponses;
import com.como.server.mapper.BoardMapper;
import com.como.server.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;


    @Transactional(readOnly = true)
    public List<BoardSimpleResponses> getBoardsWithType(String type) {
        return null;
//        return boardMapper.findAllByType(type).stream()
//                .map(BoardSimpleResponses::from)
//                .toList();
    }

}
