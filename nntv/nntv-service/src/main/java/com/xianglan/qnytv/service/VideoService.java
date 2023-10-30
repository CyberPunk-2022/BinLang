package com.xianglan.qnytv.service;

import com.xianglan.qnytv.domain.JsonResponse;
import com.xianglan.qnytv.domain.Video;
import com.xianglan.qnytv.domain.exception.ConditionException;
import com.xianglan.qnytv.mapper.UserMapper;
import com.xianglan.qnytv.mapper.VideoMapper;
import com.xianglan.qnytv.service.vo.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserMapper userMapper;
    public List<VideoVo> selectVideoListByUserId(Long userId){
        if(userId==null){
            throw new ConditionException("id参数不合法");
        }
        List<Video> videoList=videoMapper.selectVideosByUserId(userId);
        List<VideoVo> videoVoList=new ArrayList<>();
        for (Video video : videoList) {
            VideoVo videoVo = new VideoVo();
            BeanUtils.copyProperties(video,videoVo);
            videoVo.setAuthor(userMapper.getUserById(userId));
            videoVoList.add(videoVo);
        }
        return videoVoList;
    }
}
