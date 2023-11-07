package com.xianglan.qnytv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xianglan.qnytv.domain.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;


public interface AuthRoleMenuMapper extends BaseMapper<AuthRoleMenu> {

    List<AuthRoleMenu> getAuthRoleMenusByRoleIds(Set<Long> roleIdSet);
}