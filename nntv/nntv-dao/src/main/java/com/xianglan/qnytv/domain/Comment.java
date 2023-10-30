package com.xianglan.qnytv.domain;

import java.util.Date;

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
