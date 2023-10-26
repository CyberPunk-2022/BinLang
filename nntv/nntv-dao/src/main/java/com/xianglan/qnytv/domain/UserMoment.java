package com.xianglan.qnytv.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserMoment {
    private Long id;

    private Long userId;

    private String type;

    private Long contentId;

    private Date createTime;

    private Date updateTime;
}
