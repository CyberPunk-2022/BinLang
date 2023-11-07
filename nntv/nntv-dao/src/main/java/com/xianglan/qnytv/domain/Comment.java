package com.xianglan.qnytv.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("t_video_comment")
@Data
public class Comment {
    private Long id;

    private Long relativeId;
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

    private Long rootId;

    private Date createTime;
}
