package com.xianglan.qnytv.domain.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户角色
 */
@Data
@TableName("t_user_role")
public class UserRole {
    private Long id;

    private Long userId;

    private Long roleId;

    private String roleName;

    private String roleCode;

    private Date createTime;
}
