package com.xianglan.qnytv.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
@TableName("t_comment")
public class Comment {
    private Long id;
    private Long videoId;
    /**
     * 作者id
     */
    private Long userId;
    /**
     * 评论内容
     */
    private String comment;

    /**
     * 回复的用户id
     */
    private Long replyUserId;

    private Date createTime;
}
