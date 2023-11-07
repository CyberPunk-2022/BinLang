package com.xianglan.qnytv.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xianglan.qnytv.aspect.SkipAuth;
import com.xianglan.qnytv.domain.Comment;
import com.xianglan.qnytv.domain.base.JsonResponse;
import com.xianglan.qnytv.domain.vo.CommentVO;
import com.xianglan.qnytv.mapper.CommentMapper;
import com.xianglan.qnytv.service.entity.req.GetCommentListReq;
import com.xianglan.qnytv.service.entity.req.SubmitCommentInfoReq;
import com.xianglan.qnytv.support.UserSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/comment")
@RestController
@Slf4j
public class CommentController {


    @Resource
    private CommentMapper commentMapper;


    @Autowired
    private UserSupport support;

    /**
     * 获取视频列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getPageList")
    @SkipAuth
    public JsonResponse<Object> getPageList(@RequestBody GetCommentListReq request) {
        Page<Comment> page = new Page<>(request.getPage(), request.getPageSize());
        QueryWrapper<Comment> qw = new QueryWrapper<>();
        qw.eq("relativeId", request.getVideoId());
        qw.orderByDesc("createTime");
        Page<Comment> poPage = commentMapper.selectPage(page, qw);


        Page<CommentVO> pageVO = new Page<>(request.getPage(), request.getPageSize());

        List<CommentVO> list = new ArrayList<>();
        List<Comment> records = poPage.getRecords();

        if (CollectionUtil.isNotEmpty(records)) {
            for (Comment record : records) {
                CommentVO vo = new CommentVO();
                //todo 这里需要查询评论人昵称
                vo.setCommentName("测试评论人");
                vo.setContent(record.getComment());
                vo.setCreateTime(record.getCreateTime());
                list.add(vo);
            }
        }
        pageVO.setRecords(list);
        return JsonResponse.success(pageVO);
    }

    /**
     * 获取视频列表
     *
     * @param request
     * @return
     */
    @PostMapping("/submitComment")
    public JsonResponse<String> submitComment(@RequestBody SubmitCommentInfoReq request) {
        Comment comment = new Comment();
        comment.setRelativeId(request.getVideoId());
        comment.setUserId(support.getCurrentUseUserId());
        comment.setComment(request.getContent());
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);

        return JsonResponse.success();

    }
}
