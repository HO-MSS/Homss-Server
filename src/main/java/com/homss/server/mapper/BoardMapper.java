package com.homss.server.mapper;

import com.homss.server.dto.response.BoardDetailResponse;
import com.homss.server.dto.response.BoardSimpleResponse;
import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardStatus;
import com.homss.server.model.board.BoardType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    void save(Board board);

    void deleteAll();

    List<Board> findAll();

    List<BoardSimpleResponse> findAllByType(BoardType boardType, String keyword, Long offset, Integer pageSize);

    Long countByType(BoardType boardType, String keyword);

    void increaseViewNum(Long boardId);

    BoardDetailResponse findDetailById(Long memberId, Long boardId);

    Optional<Board> findById(Long boardId);

    void changeStatus(Long boardId, BoardStatus boardStatus);

}
