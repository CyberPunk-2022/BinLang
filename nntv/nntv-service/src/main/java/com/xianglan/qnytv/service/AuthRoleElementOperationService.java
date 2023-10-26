package com.xianglan.qnytv.service;


import com.xianglan.qnytv.domain.auth.AuthRoleElementOperation;
import com.xianglan.qnytv.mapper.AuthRoleElementOperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleElementOperationService {

    @Autowired
    private AuthRoleElementOperationMapper authRoleElementOperationMapper;

    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
        return authRoleElementOperationMapper.getRoleElementOperationsByRoleIds(roleIdSet);
    }
}
