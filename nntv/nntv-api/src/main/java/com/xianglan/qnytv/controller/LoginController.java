package com.xianglan.qnytv.controller;

import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.jwt.JWT;
import com.xianglan.qnytv.domain.User;
import com.xianglan.qnytv.domain.base.JsonResponse;
import com.xianglan.qnytv.domain.constant.StatusEnum;
import com.xianglan.qnytv.mapper.UserMapper;
import com.xianglan.qnytv.util.SingletonMapCache;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录模块
 */
@RestController
@RequestMapping("account")
public class LoginController {


    @Autowired
    private UserMapper userMapper;

    /**
     * 登录或者注册
     *
     * @param phone
     * @param veryCode
     * @return
     */

    @PostMapping("/loginOrRegister")
    public JsonResponse<Object> login(String phone, String veryCode) {
        User user = userMapper.getUserByPhone(phone);
        boolean isPhone = PhoneUtil.isPhone(phone);
        if (ObjectUtils.isEmpty(user) && !isPhone) {
            return new JsonResponse<>(StatusEnum.FAIL.getCode(), StatusEnum.FAIL.getMsg());
        } else {
            //todo 如果是注册的话校验一下发送的短信验证码
            //这里默认不发送，只要是符合是手机号的直接返回token

            String token = RandomUtil.randomString(32);
            //是手机号且这个用户不存在
            if (isPhone && ObjectUtils.isEmpty(user)) {
                user = new User();
                user.setPhone(phone);
                user.setPassword(token); //todo 先存个token
                userMapper.insert(user);
            }

            Map<String, String> res = new HashMap<>();
            res.put("token", token);
            // 存入缓存中
            SingletonMapCache.getInstance().put(token, user.getId());
            return JsonResponse.success(res);
        }
    }

}