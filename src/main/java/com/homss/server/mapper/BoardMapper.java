package com.homss.server.mapper;

import com.homss.server.model.board.Board;
import com.homss.server.model.board.BoardType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void save(Board board);
    void deleteAll();
    List<Board> findAll();

    List<Board> findAllByType(BoardType boardType, Long offset, Integer pageSize);

    Long countByType(BoardType boardType);
}
