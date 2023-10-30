package com.xianglan.qnytv.service.vo;

import com.xianglan.qnytv.domain.Comment;
import com.xianglan.qnytv.domain.User;
import lombok.Data;

import java.util.Date;

@Data
public class VideoVo {
    private Long id;
    private String url;

    /**
     * 视频作者
     */
    private User author;


}

