package com.xianglan.qnytv.service.entity.req;

import lombok.Data;

@Data
public class SubmitCommentInfoReq {
    private Long videoId;
    private String content;
}
