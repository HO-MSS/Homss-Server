package com.homss.server.mapper;

import com.homss.server.model.board.BoardLike;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardLikeMapper {

    void deleteAll();

    List<BoardLike> findAll();

    Boolean findLikeStatus(Long boardId, Long memberId);

    void save(BoardLike boardLike);

    void deleteLike(Long boardId, Long memberId);

}
