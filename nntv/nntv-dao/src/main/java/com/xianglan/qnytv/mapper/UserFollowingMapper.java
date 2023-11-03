package com.xianglan.qnytv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xianglan.qnytv.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserFollowingMapper extends BaseMapper<UserFollowing> {
    Integer deleteUserFollowing(@Param("userId") Long userId, @Param("followingId") Long followingId);

    Integer addUserFollowing(UserFollowing userFollowing);

    List<UserFollowing> getUserFollowings(Long userId);

    List<UserFollowing> getUserFans(Long userId);
}
