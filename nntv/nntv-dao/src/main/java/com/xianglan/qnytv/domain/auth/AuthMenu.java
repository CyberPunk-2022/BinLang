package com.xianglan.qnytv.domain.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_auth_menu")
public class AuthMenu {
    private Long id;

    private String name;

    private String code;

    private Date createTime;

    private Date updateTime;
}
