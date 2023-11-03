package com.xianglan.qnytv.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianglan.qnytv.domain.VideoPo;
import com.xianglan.qnytv.domain.exception.ConditionException;
import com.xianglan.qnytv.mapper.UserMapper;
import com.xianglan.qnytv.mapper.VideoMapper;
import com.xianglan.qnytv.service.entity.req.GetVideoListRequest;
import com.xianglan.qnytv.service.entity.req.SaveVideoRequest;
import com.xianglan.qnytv.service.vo.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
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
        List<VideoPo> videoEntityList=videoMapper.selectVideosByUserId(userId);
        List<VideoVo> videoVoList=new ArrayList<>();
        for (VideoPo videoEntity : videoEntityList) {
            VideoVo videoVo = new VideoVo();
            BeanUtils.copyProperties(videoEntity,videoVo);
            videoVo.setAuthor(userMapper.getUserById(userId));
            videoVoList.add(videoVo);
        }
        return videoVoList;
    }

    public Page<VideoPo> getPageList(GetVideoListRequest request) {
        QueryWrapper<VideoPo> queryWrapper = new QueryWrapper<>();
        Page<VideoPo> page = new Page<>(request.getPage(), request.getPageSize());

        if (StringUtils.isNotBlank(request.getKeyword())) {
            //先用关键字查询描述信
            queryWrapper.like("description", request.getKeyword());
        }

        Page<VideoPo> poPage = videoMapper.selectPage(page, queryWrapper);


        return poPage;
    }

    public void saveVideo(SaveVideoRequest request) {
        VideoPo po = new VideoPo();

        po.setUrl(request.getUrl());
        po.setThumbnail(request.getPostUrl());
        po.setTitle(request.getTitle());
        po.setType(request.getVideoType());

        videoMapper.insert(po);
    }
}
