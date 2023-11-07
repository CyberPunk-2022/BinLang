package com.xianglan.qnytv.controller;

import com.xianglan.qnytv.domain.UserMoment;
import com.xianglan.qnytv.domain.annotation.ApiLimitedRole;
import com.xianglan.qnytv.domain.annotation.DataLimited;
import com.xianglan.qnytv.domain.base.JsonResponse;
import com.xianglan.qnytv.domain.constant.AuthRoleConstant;
import com.xianglan.qnytv.service.UserMomentsService;
import com.xianglan.qnytv.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserMomentsController {
    @Autowired
    private UserMomentsService userMomentsService;

    @Autowired
    private UserSupport userSupport;

    /**
     * 新增用户动态
     * @param userMoment
     * @return
     * @throws Exception
     */
    @ApiLimitedRole(limitedRoleCodeList = {AuthRoleConstant.ROLE_LV0})
    @DataLimited
    @PostMapping("/user-moments")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoment userMoment) throws Exception {
        Long userId = userSupport.getCurrentUserId();
        userMoment.setUserId(userId);
        userMomentsService.addUserMoments(userMoment);
        return JsonResponse.success();
    }

    /**
     * 查询订阅动态
     * @return
     */
    @GetMapping("/user-subscribed-moments")
    public JsonResponse<List<UserMoment>> getUserSubscribedMoments(){
        Long userId = userSupport.getCurrentUserId();
        List<UserMoment> list = userMomentsService.getUserSubscribedMoments(userId);
        return new JsonResponse<>(list);
    }

}
