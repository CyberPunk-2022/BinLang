package com.xianglan.qnytv.service;

import com.xianglan.qnytv.domain.auth.AuthRole;
import com.xianglan.qnytv.domain.auth.AuthRoleElementOperation;
import com.xianglan.qnytv.domain.auth.AuthRoleMenu;
import com.xianglan.qnytv.mapper.AuthRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleService {

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Autowired
    private AuthRoleElementOperationService authRoleElementOperationService;

    @Autowired
    private AuthRoleMenuService authRoleMenuService;

    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
        return authRoleElementOperationService.getRoleElementOperationsByRoleIds(roleIdSet);
    }

    public List<AuthRoleMenu> getAuthRoleMenusByRoleIds(Set<Long> roleIdSet) {
        return authRoleMenuService.getAuthRoleMenusByRoleIds(roleIdSet);
    }

    public AuthRole getRoleByCode(String code) {
        return authRoleMapper.getRoleByCode(code);
    }
}
