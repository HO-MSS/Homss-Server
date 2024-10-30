package com.homss.server.service;

import com.homss.server.dto.response.BoardSimpleResponses;
import com.homss.server.mapper.BoardMapper;
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
