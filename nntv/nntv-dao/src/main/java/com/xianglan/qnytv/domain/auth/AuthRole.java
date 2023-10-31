package com.xianglan.qnytv.domain.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_auth_role")
public class AuthRole {
    private Long id;

    private String name;

    private String code;

    private Date createTime;

    private Date updateTime;
}
