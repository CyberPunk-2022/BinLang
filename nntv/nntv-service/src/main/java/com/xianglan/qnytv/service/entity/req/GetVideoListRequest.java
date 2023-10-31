package com.xianglan.qnytv.service.entity.req;

import lombok.Data;

/**
 * 查询视频的列表
 */
@Data
public class GetVideoListRequest {

    private Integer page;

    private Integer pageSize;

    private String keyword;
}