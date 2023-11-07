package com.xianglan.qnytv.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.StringUtils;
import com.xianglan.qnytv.domain.RefreshTokenDetail;
import com.xianglan.qnytv.domain.User;
import com.xianglan.qnytv.domain.UserInfo;
import com.xianglan.qnytv.domain.base.PageResult;
import com.xianglan.qnytv.domain.constant.UserConstant;
import com.xianglan.qnytv.domain.exception.ConditionException;
import com.xianglan.qnytv.mapper.UserInfoMapper;
import com.xianglan.qnytv.mapper.UserMapper;
import com.xianglan.qnytv.service.util.MD5Util;
import com.xianglan.qnytv.service.util.RSAUtil;
import com.xianglan.qnytv.service.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAuthService userAuthService;

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
        // 添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreateTime(now);
        userMapper.addUserInfo(userInfo);
        // 添加用户默认权限角色
        userAuthService.addUserDefaultRole(user.getId());
    }

    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    public String login(User user) throws Exception {
        String phone = user.getPhone() == null ? "" : user.getPhone();
        String email = user.getEmail() == null ? "" : user.getEmail();
        if (StringUtils.isNullOrEmpty(phone) && StringUtils.isNullOrEmpty(email)) {
            throw new ConditionException("参数异常！");
        }
        User dbUser = userMapper.getUserByPhoneOrEmail(phone, email);
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

    public void logout(String refreshToken, Long userId) {
        userMapper.deleteRefreshToken(refreshToken, userId);
    }

    // TODO
    public Map<String, Object> loginForDts(User user) throws Exception {
        String phone = user.getPhone() == null ? "" : user.getPhone();
        String email = user.getEmail() == null ? "" : user.getEmail();
        if (StringUtils.isNullOrEmpty(phone) && StringUtils.isNullOrEmpty(email)) {
            throw new ConditionException("参数异常！");
        }
        User dbUser = userMapper.getUserByPhoneOrEmail(phone, email);
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
        log.info("rawPassword={}", password);
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if (!md5Password.equals(dbUser.getPassword())) {
            throw new ConditionException("密码错误！");
        }
        Long userId = dbUser.getId();
        String accessToken = TokenUtil.generateToken(userId);
        String refreshToken = TokenUtil.generateRefreshToken(userId);
        //保存refresh token到数据库
        userMapper.deleteRefreshToken(refreshToken, userId);
        userMapper.addRefreshToken(refreshToken, userId, new Date());
        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        return result;
    }

    public String refreshAccessToken(String refreshToken) throws Exception {
        RefreshTokenDetail refreshTokenDetail = userMapper.getRefreshTokenDetail(refreshToken);
        if (refreshTokenDetail == null) {
            throw new ConditionException("555", "token过期！");
        }
        Long userId = refreshTokenDetail.getUserId();
        return TokenUtil.generateToken(userId);
    }

    public UserInfo getUserInfo(Long userId) {
        UserInfo userInfo = userMapper.getUserInfoByUserId(userId);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setNick("新用户" + RandomUtil.randomString(5));
            userInfo.setUserId(userId);
            userInfoMapper.insert(userInfo);
            return userInfo;
        }
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        UserInfo userInfo1 = userInfoMapper.selectOne(queryWrapper);
        return userInfo1;
    }

    public void updateUserInfos(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userInfo.getUserId());

        DateTime parse = DateUtil.parse(userInfo.getBirth(), "yyyy-MM-dd");
        String birth = DateUtil.format(parse, "yyyy-MM-dd");

        userInfo.setBirth(birth);
        userInfoMapper.update(userInfo, queryWrapper);
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
        params.put("start", (no - 1) * size);
        params.put("limit", size);
        Integer total = userMapper.pageCountUserInfos(params);
        List<UserInfo> list = new ArrayList<>();
        if (total > 0) {
            list = userMapper.pageListUserInfos(params);
        }
        return new PageResult<>(total, list);
    }
}
