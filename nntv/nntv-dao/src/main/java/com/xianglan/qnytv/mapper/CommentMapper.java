package com.xianglan.qnytv.mapper;

import com.xianglan.qnytv.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> getCommentsByVideoId(Long videoId);
}
