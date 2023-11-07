package com.xianglan.qnytv.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "t_video_like")
@Data
public class LikRecord {

    private Long id;
    private Long userId;
    private Long relativeId;
    private Data createTime;
    private Integer isDel;
};
