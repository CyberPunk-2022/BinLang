package com.xianglan.qnytv.service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianglan.qnytv.domain.Comment;
import com.xianglan.qnytv.domain.LikRecord;
import com.xianglan.qnytv.domain.UserInfo;
import com.xianglan.qnytv.domain.VideoPo;
import com.xianglan.qnytv.domain.exception.ConditionException;
import com.xianglan.qnytv.domain.vo.VideoVO;
import com.xianglan.qnytv.mapper.*;
import com.xianglan.qnytv.service.entity.req.GetVideoListRequest;
import com.xianglan.qnytv.service.entity.req.LikeVideoReq;
import com.xianglan.qnytv.service.entity.req.SaveVideoRequest;
import com.xianglan.qnytv.service.vo.VideoVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserMapper userMapper;


    @Resource
    private CommentMapper commentMapper;


    @Autowired
    private UserInfoMapper userInfoMapper;


    @Autowired
    private LikRecordMapper likRecordMapper;


    public List<VideoVo> selectVideoListByUserId(Long userId) {
        if (userId == null) {
            throw new ConditionException("id参数不合法");
        }
        List<VideoPo> videoEntityList = videoMapper.selectVideosByUserId(userId);
        List<VideoVo> videoVoList = new ArrayList<>();
        for (VideoPo videoEntity : videoEntityList) {
            VideoVo videoVo = new VideoVo();
            BeanUtils.copyProperties(videoEntity, videoVo);
            videoVo.setAuthor(userMapper.getUserById(userId));
            videoVoList.add(videoVo);
        }
        return videoVoList;
    }

    public Page<VideoVO> getPageList(GetVideoListRequest request) {
        QueryWrapper<VideoPo> queryWrapper = new QueryWrapper<>();
        Page<VideoPo> page = new Page<>(request.getPage(), request.getPageSize());

        if (StringUtils.isNotBlank(request.getKeyword())) {
            //先用关键字查询描述信
            queryWrapper.like("description", request.getKeyword()).or().like("title", request.getKeyword());
        }
        //根据分类查询
        if ((StringUtils.isNotBlank(request.getCategoryId()))) {
            queryWrapper.eq("mainCategoryId", request.getCategoryId());
        }

        if (StringUtils.isNotBlank(request.getAuthorId())) {
            queryWrapper.eq("userId", request.getAuthorId());
        }

        Page<VideoVO> pageVO = new Page<>(request.getPage(), request.getPageSize());
        Page<VideoPo> poPage = videoMapper.selectPage(page, queryWrapper);

        List<VideoVO> list = getVideoVOS(poPage);

        BeanUtils.copyProperties(poPage, pageVO);
        pageVO.setRecords(list);
        return pageVO;
    }

    private List<VideoVO> getVideoVOS(Page<VideoPo> poPage) {
        List<VideoVO> list = new ArrayList<>();
        if (poPage.getRecords() != null) {
            for (VideoPo record : poPage.getRecords()) {
                QueryWrapper<Comment> qw = new QueryWrapper<>();
                qw.eq("relativeId", record.getId());

                //todo 这个放这里不是很合适，高QPS不能这样设计，而是缓存一个
                long count = commentMapper.selectCount(qw);


                System.out.println("assas" + record.getId());
                VideoVO videoVO = new VideoVO();
                videoVO.setVideoId(record.getId().toString());
                videoVO.setAuthorId(record.getUserId().toString());
                videoVO.setCategoryId(record.getMainCategoryId());
                videoVO.setUrl(record.getUrl());
                videoVO.setTitle(record.getTitle());
                videoVO.setPostUrl(record.getThumbnail());
                videoVO.setCommentNum(Long.valueOf(count).toString());
                //todo 测试作者

                QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("userId", record.getUserId());

                UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
                videoVO.setAuthorName(userInfo.getNick());
                videoVO.setUserInfo(userInfo);
                videoVO.setDescription(record.getDescription());
                videoVO.setCreateTime(record.getCreateTime());
                videoVO.setStars(record.getStars().toString());
                list.add(videoVO);
            }
        }
        return list;
    }

    public void saveVideo(SaveVideoRequest request) {
        VideoPo po = new VideoPo();

        po.setUrl(request.getUrl());
        po.setUserId(request.getUserId());
        po.setThumbnail(request.getPostUrl());
        po.setTitle(request.getTitle());
        po.setType(request.getVideoType());
        po.setMainCategoryId(request.getCategoryId());
        po.setDescription(request.getVideoDesc());
        videoMapper.insert(po);
    }

    public VideoPo likeVideo(LikeVideoReq req) {

        QueryWrapper<VideoPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", req.getVideoId());
        VideoPo po = videoMapper.selectOne(queryWrapper);


        boolean hasLike = hasLike(po, req.getUserId());
        long num;
        if (hasLike) {
            num = po.getStars() - 1;
        } else {
            num = po.getStars() + 1;
        }
        if (po.getStars() < 0) {
            po.setStars(0L);
        }

        po.setStars(num);
        videoMapper.updateById(po);
        return po;
    }

    private boolean hasLike(VideoPo po, String userId) {

        QueryWrapper<LikRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("relativeId", po.getId());
        LikRecord likRecord = likRecordMapper.selectOne(queryWrapper);

        if (ObjectUtils.isEmpty(likRecord)) {
            likRecord = new LikRecord();
            likRecord.setUserId(Long.valueOf(userId));
            likRecord.setRelativeId(po.getId());
            likRecord.setIsDel(0);
            likRecordMapper.insert(likRecord);
        }
        boolean flag;
        if (likRecord.getIsDel() == 1) {
            likRecord.setIsDel(0);
            flag = false;
        } else {
            likRecord.setIsDel(1);
            flag = true;
        }
        likRecordMapper.updateById(likRecord);
        return flag;
    }
}
