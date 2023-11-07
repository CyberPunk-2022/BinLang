package com.xianglan.qnytv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xianglan.qnytv.domain.auth.AuthRoleElementOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Set;

@Mapper
public interface AuthRoleElementOperationMapper extends BaseMapper<AuthRoleElementOperation> {

    List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(@Param("roleIdSet") Set<Long> roleIdSet);
}