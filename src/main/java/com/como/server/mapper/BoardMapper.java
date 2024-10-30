package com.como.server.mapper;

import com.como.server.model.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void save(Board board);
//    List<Board> findAllByType(String type);
}
