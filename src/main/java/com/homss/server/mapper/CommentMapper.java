package com.homss.server.mapper;

import com.homss.server.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    void save(Comment newComment);
    void deleteAll();
    List<Comment> findAll();
    Optional<Comment> findById(Long commentId);
}
