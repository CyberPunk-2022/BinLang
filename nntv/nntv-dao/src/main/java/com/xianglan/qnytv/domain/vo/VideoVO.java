package com.xianglan.qnytv.domain.vo;

import lombok.Data;

@Data
public class VideoVO {

    private Long videoId; //视频id

    private Long authorId; //创作者id

    private String categoryId; //分类Id

    private String url; //视频url

    private String title; //视频标题

    private String postUrl; //视频分后面

    private String authorName; //视频作者名称

}