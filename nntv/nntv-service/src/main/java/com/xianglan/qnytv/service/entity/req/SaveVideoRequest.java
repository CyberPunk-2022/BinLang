package com.xianglan.qnytv.service.entity.req;

import lombok.Data;

/**
 * 保存视频
 */
@Data
public class SaveVideoRequest {

    private String url; // 视频地址
    private String postUrl; // 封面url

    private String title; // 视频标题

    private String videoType; // 视频类型

    private String categoryId; // 视频分类

    private String videoDesc;//视频描述

    private Long userId;//作者id

}
