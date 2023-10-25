package com.xianglan.qnytv.controller;

import com.alibaba.fastjson.JSONObject;
import com.xianglan.qnytv.domain.JsonResponse;
import com.xianglan.qnytv.domain.PageResult;
import com.xianglan.qnytv.domain.User;
import com.xianglan.qnytv.domain.UserInfo;
import com.xianglan.qnytv.service.UserFollowingService;
import com.xianglan.qnytv.service.UserService;
import com.xianglan.qnytv.service.util.RSAUtil;
import com.xianglan.qnytv.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserFollowingService userFollowingService;

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        System.out.println("get public key");
        return new JsonResponse<>(RSAUtil.getPublicKeyStr());
    }

    /**
     * 用户注册
     */
    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        System.out.println(user);
        userService.addUser(user);
        return JsonResponse.success();
    }

    /**
     * 用户登录
     */
    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user) throws Exception{
        String token = userService.login(user);
        //System.out.println("login user="+user);
        return new JsonResponse<>(token);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/user-infos")
    public JsonResponse<String> updateUserInfos(@RequestBody UserInfo userInfo){
        Long userId = userSupport.getCurrentUserId();
        userInfo.setUserId(userId);
        userService.updateUserInfos(userInfo);
        return JsonResponse.success();
    }
    @PutMapping("/users")
    public JsonResponse<String> updateUsers(@RequestBody User user) throws Exception{
        Long userId = userSupport.getCurrentUserId();
        user.setId(userId);
        userService.updateUsers(user);
        return JsonResponse.success();
    }



    @GetMapping("/user-infos")
    public JsonResponse<PageResult<UserInfo>> pageListUserInfos(@RequestParam Integer no, @RequestParam Integer size, String nick){
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("no", no);
        params.put("size", size);
        params.put("nick", nick);
        params.put("userId", userId);
        PageResult<UserInfo> result = userService.pageListUserInfos(params);
        if(result.getTotal() > 0){
            List<UserInfo> checkedUserInfoList = userFollowingService.checkFollowingStatus(result.getList(), userId);
            result.setList(checkedUserInfoList);
        }
        return new JsonResponse<>(result);
    }



}
