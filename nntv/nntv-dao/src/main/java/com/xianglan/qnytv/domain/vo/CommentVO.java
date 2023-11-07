package com.xianglan.qnytv.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVO {

    private Long commentId;
    private String commentName;

    private String content;

    private Date createTime;
}
