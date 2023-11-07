package com.xianglan.qnytv.domain.auth;

import lombok.Data;

import java.util.List;
// TODO
@Data
public class UserAuthorities {
    List<AuthRoleElementOperation> roleElementOperationList;

    List<AuthRoleMenu> roleMenuList;
}
