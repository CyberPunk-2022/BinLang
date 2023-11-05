package com.xianglan.qnytv.controller;

import com.xianglan.qnytv.domain.base.JsonResponse;
import com.xianglan.qnytv.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/video")
@Deprecated
public class VideoController {
    @Autowired
    private VideoService videoService;
    /**
     * 根据作者id获取作品列表
     * @param userId
     * @return
     */
    @GetMapping("/user")
    public JsonResponse<List> getUserVideoList(@RequestBody Long userId){
        return new JsonResponse<>(videoService.selectVideoListByUserId(userId));
    }
}
