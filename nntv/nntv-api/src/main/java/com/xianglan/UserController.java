package com.xianglan;

import com.xianglan.qnytv.domain.JsonResponse;
import com.xianglan.qnytv.domain.User;
import com.xianglan.qnytv.service.UserService;
import com.xianglan.qnytv.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        return new JsonResponse<>(RSAUtil.getPublicKeyStr());
    }

    /**
     * 用户注册
     */
    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return JsonResponse.success();
    }

    /**
     * 用户登录
     */
    @PostMapping("/user-token")
    public JsonResponse<String> login(@RequestBody User user) throws Exception{
        String token = userService.login(user);
        return new JsonResponse<>(token);
    }
}
