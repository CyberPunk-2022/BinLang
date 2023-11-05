package com.xianglan.qnytv.controller;

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
        if (ObjectUtils.isEmpty(user)) {
            return new JsonResponse<>(StatusEnum.FAIL.getCode(), StatusEnum.FAIL.getMsg());
        } else {
            //todo 如果是注册的话校验一下发送的短信验证码
            //这里默认不发送，只要是符合是手机号的直接返回token
            Map<String, String> res = new HashMap<>();
            String token = RandomUtil.randomString(32);
            res.put("token", token);
            SingletonMapCache.getInstance().put(token, token);
            return JsonResponse.success(res);
        }
    }

}