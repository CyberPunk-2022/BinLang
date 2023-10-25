package com.xianglan.qnytv.service;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.xianglan.qnytv.domain.PageResult;
import com.xianglan.qnytv.domain.User;
import com.xianglan.qnytv.domain.UserInfo;
import com.xianglan.qnytv.domain.constant.UserConstant;
import com.xianglan.qnytv.domain.exception.ConditionException;
import com.xianglan.qnytv.mapper.UserMapper;
import com.xianglan.qnytv.service.util.MD5Util;
import com.xianglan.qnytv.service.util.RSAUtil;
import com.xianglan.qnytv.service.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void addUser(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空！");
        }
        User dbUser = this.getUserByPhone(phone);
        if (dbUser != null) {
            throw new ConditionException("该手机号已经注册！");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        //System.out.println("password="+password);
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败！");
        }
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);
        userMapper.addUser(user);
        //添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId())
                .setNick(UserConstant.DEFAULT_NICK)
                .setBirth(UserConstant.DEFAULT_BIRTH)
                .setGender(UserConstant.GENDER_MALE)
                .setCreateTime(now);
    }

    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    public String login(User user) throws Exception {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空！");
        }
        User dbUser = this.getUserByPhone(phone);
        if (dbUser == null) {
            throw new ConditionException("当前用户不存在！");
        }
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败！");
        }
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if (!md5Password.equals(dbUser.getPassword())) {
            throw new ConditionException("密码错误！");
        }
        return TokenUtil.generateToken(dbUser.getId());
    }

    public User getUserInfo(Long userId) {
        User user = userMapper.getUserById(userId);
        UserInfo userInfo = userMapper.getUserInfoByUserId(userId);
        user.setUserInfo(userInfo);
        return user;
    }

    public void updateUserInfos(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        userMapper.updateUserInfos(userInfo);
    }

    public User getUserById(Long followingId) {
        return userMapper.getUserById(followingId);
    }

    public void updateUsers(User user) throws Exception {
        Long id = user.getId();
        User dbUser = userMapper.getUserById(id);
        if (dbUser == null) {
            throw new ConditionException("用户不存在！");
        }
        if (!StringUtils.isNullOrEmpty(user.getPassword())) {
            String rawPassword = RSAUtil.decrypt(user.getPassword());
            String md5Password = MD5Util.sign(rawPassword, dbUser.getSalt(), "UTF-8");
            user.setPassword(md5Password);
        }
        user.setUpdateTime(new Date());
        userMapper.updateUsers(user);
    }

    public List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList) {
        return userMapper.getUserInfoByUserIds(userIdList);
    }

    public PageResult<UserInfo> pageListUserInfos(JSONObject params) {
        Integer no = params.getInteger("no");
        Integer size = params.getInteger("size");
        params.put("start", (no-1)*size);
        params.put("limit", size);
        Integer total = userMapper.pageCountUserInfos(params);
        List<UserInfo> list = new ArrayList<>();
        if(total > 0){
            list = userMapper.pageListUserInfos(params);
        }
        return new PageResult<>(total, list);
    }
}
