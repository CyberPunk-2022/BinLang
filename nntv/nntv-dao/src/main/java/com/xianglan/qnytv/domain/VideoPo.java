package com.xianglan.qnytv.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_video")
public class VideoPo {
    private Long id;
    /**
     * 作者id
     */
    private Long userId;
    /**
     * 视频链接
     */
    private String url;
    /**
     * 封面链接
     */
    private String thumbnail;
    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频分类 0原创，1转载
     */
    private String type;
    /**
     * 视频时长
     */
    private String duration;
    /**
     * 所在分区
     */
    private String area;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 0正常，1已删除
     */
    private Short isDel;
    /**
     * 点赞数
     */
    private Long stars;
    /**
     * 评论数
     */
    private Long comments;
    /**
     * 主要分类
     */
    private String mainCategoryId;
    /**
     * 子分类ids
     */
    private String childCategoryIds;
    /**
     * 描述信息
     */
    private String description;
}
