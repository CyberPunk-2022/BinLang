package com.xianglan.qnytv.service.entity.req;

import lombok.Data;

/**
 * 查询视频的列表
 */
@Data
public class GetVideoListRequest {

    private Integer page=1;

    private Integer pageSize=10;

    private String keyword;
}