package com.xianglan.qnytv.mapper;

import com.xianglan.qnytv.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMomentsMapper {
    Integer addUserMoments(UserMoment userMoment);
}
