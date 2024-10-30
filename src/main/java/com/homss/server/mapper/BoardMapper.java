package com.homss.server.mapper;

import com.homss.server.model.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    void save(Board board);
//    List<Board> findAllByType(String type);
}
