package com.xianglan.qnytv.service;

import com.xianglan.qnytv.domain.FollowingGroup;
import com.xianglan.qnytv.mapper.FollowingGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowingGroupService {
    @Autowired
    private FollowingGroupMapper followingGroupMapper;

    public FollowingGroup getByType(String type){
        return followingGroupMapper.getByType(type);
    }

    public FollowingGroup getById(Long id){
        return followingGroupMapper.getById(id);
    }

    public List<FollowingGroup> getByUserId(Long userId) {
        return followingGroupMapper.getByUserId(userId);
    }

    public void addFollowingGroup(FollowingGroup followingGroup) {
        followingGroupMapper.addFollowingGroup(followingGroup);
    }

    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupMapper.getUserFollowingGroups(userId);
    }

}
