package com.homss.server.mapper;

import com.homss.server.model.comment.CommentLike;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentLikeMapper {

    void deleteAll();

    List<CommentLike> findAll();

    Boolean findLikeStatus(Long commentId, Long memberId);

    void deleteLike(Long commentId, Long memberId);

    void save(CommentLike newCommentLike);

}
