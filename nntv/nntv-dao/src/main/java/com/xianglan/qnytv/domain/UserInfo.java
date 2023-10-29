package com.xianglan.qnytv.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@Accessors(chain = true)
public class UserInfo {
    private Long id;

    private Long userId;

    private String nick;

    private String avatar;

    private String sign;

    private String gender;

    private String birth;

    private Date createTime;

    private Date updateTime;
    /**
     * 用户是否关注，查询粉丝列表专用
     */
    private Boolean followed;
}
