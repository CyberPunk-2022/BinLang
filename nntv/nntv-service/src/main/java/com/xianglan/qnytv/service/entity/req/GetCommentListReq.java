package com.xianglan.qnytv.service.entity.req;

import lombok.Data;

@Data
public class GetCommentListReq {

    private Integer page = 1;

    private Integer pageSize = 10;

    private Long videoId;

}
