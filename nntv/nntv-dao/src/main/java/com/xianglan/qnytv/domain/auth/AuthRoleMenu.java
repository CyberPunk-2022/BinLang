package com.xianglan.qnytv.domain.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("t_auth_role_menu")
public class AuthRoleMenu {
    private Long id;

    private Long roleId;

    private Long menuId;

    private Date createTime;

    private AuthMenu authMenu;
}
