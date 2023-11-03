package com.xianglan.qnytv.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_user_moments")
public class UserMoment {
    private Long id;

    private Long userId;

    private String type;

    private Long contentId;

    private Date createTime;

    private Date updateTime;
}
