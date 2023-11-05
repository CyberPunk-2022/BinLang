package com.xianglan.qnytv.service;
import com.xianglan.qnytv.domain.vo.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianglan.qnytv.domain.VideoPo;
import com.xianglan.qnytv.domain.exception.ConditionException;
import com.xianglan.qnytv.mapper.UserMapper;
import com.xianglan.qnytv.mapper.VideoMapper;
import com.xianglan.qnytv.service.entity.req.GetVideoListRequest;
import com.xianglan.qnytv.service.entity.req.SaveVideoRequest;
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

    public List<VideoVO> selectVideoListByUserId(Long userId) {
        if (userId == null) {
            throw new ConditionException("id参数不合法");
        }
        List<VideoPo> videoEntityList = videoMapper.selectVideosByUserId(userId);
        List<VideoVO> videoVOList = new ArrayList<>();
        for (VideoPo videoEntity : videoEntityList) {
            VideoVO videoVO = new VideoVO();
            BeanUtils.copyProperties(videoEntity, videoVO);
            videoVO.setAuthorId(userId);
            videoVOList.add(videoVO);
        }
        return videoVOList;
    }

    public Page<VideoVO> getPageList(GetVideoListRequest request) {
        QueryWrapper<VideoPo> queryWrapper = new QueryWrapper<>();
        Page<VideoPo> page = new Page<>(request.getPage(), request.getPageSize());

        if (StringUtils.isNotBlank(request.getKeyword())) {
            //先用关键字查询描述信
            queryWrapper.like("description", request.getKeyword());
        }
        Page<VideoVO> pageVO = new Page<>(request.getPage(), request.getPageSize());

        Page<VideoPo> poPage = videoMapper.selectPage(page, queryWrapper);
        List<VideoVO> list = getVideoVOS(poPage);

        BeanUtils.copyProperties(poPage, pageVO);
        pageVO.setRecords(list);
        return pageVO;
    }
    private static List<VideoVO> getVideoVOS(Page<VideoPo> poPage) {
        List<VideoVO> list = new ArrayList<>();
        if (poPage.getRecords() != null) {
            for (VideoPo record : poPage.getRecords()) {
                VideoVO videoVO = new VideoVO();
                videoVO.setVideoId(record.getId());
                videoVO.setAuthorId(record.getUserId());
                videoVO.setCategoryId(record.getMainCategoryId());
                videoVO.setUrl(record.getUrl());
                videoVO.setTitle(record.getTitle());
                videoVO.setPostUrl(record.getThumbnail());

                //todo 测试作者
                videoVO.setAuthorName("测试作者");

                list.add(videoVO);
            }
        }
        return list;
    }


    public void saveVideo(SaveVideoRequest request) {
        VideoPo po = new VideoPo();

        po.setUrl(request.getUrl());
        po.setThumbnail(request.getPostUrl());
        po.setTitle(request.getTitle());
        po.setType(request.getVideoType());
        po.setMainCategoryId(request.getVideoType());
        videoMapper.insert(po);
    }
}
