package com.homss.server.mapper;

import com.homss.server.model.board.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void save(Board board);
    void deleteAll();
    List<Board> findAll();
//    List<Board> findAllByType(String type);
}
