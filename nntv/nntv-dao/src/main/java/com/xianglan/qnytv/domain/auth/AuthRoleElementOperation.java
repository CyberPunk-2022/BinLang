package com.xianglan.qnytv.domain.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("t_auth_element_operation")
public class AuthRoleElementOperation {
    private Long id;

    private Long roleId;

    private Long elementOperationId;

    private Date createTime;

    private AuthElementOperation authElementOperation;

}
