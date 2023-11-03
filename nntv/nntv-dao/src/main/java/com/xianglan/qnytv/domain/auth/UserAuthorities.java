package com.xianglan.qnytv.domain.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
// TODO
@Data
public class UserAuthorities {
    List<AuthRoleElementOperation> roleElementOperationList;

    List<AuthRoleMenu> roleMenuList;
}
