package com.xianglan.qnytv.mapper;

import com.xianglan.qnytv.domain.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoMapper {
    List<Video> selectVideosByUserId(Long userId);
}
