package com.xianglan.qnytv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xianglan.qnytv.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> getCommentsByVideoId(Long videoId);
}
