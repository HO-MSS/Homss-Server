package com.homss.server.mapper;

import com.homss.server.model.comment.Comment;
import com.homss.server.model.comment.CommentStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    void save(Comment newComment);
    void deleteAll();
    List<Comment> findAll();
    Optional<Comment> findById(Long commentId);

    void changeStatus(Long commentId, CommentStatus commentStatus);

    void edit(Long commentId, String content);
}
