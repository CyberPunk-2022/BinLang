package com.xianglan.qnytv.service;


import com.xianglan.qnytv.domain.auth.AuthRoleMenu;
import com.xianglan.qnytv.mapper.AuthRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleMenuService {

    @Autowired
    private AuthRoleMenuMapper authRoleMenuMapper;

    public List<AuthRoleMenu> getAuthRoleMenusByRoleIds(Set<Long> roleIdSet) {
        return authRoleMenuMapper.getAuthRoleMenusByRoleIds(roleIdSet);
    }
}
