package com.xianglan.qnytv.controller.video;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.xianglan.qnytv.domain.base.JsonResponse;
import com.xianglan.qnytv.domain.VideoPo;
import com.xianglan.qnytv.service.entity.req.GetVideoListRequest;
import com.xianglan.qnytv.service.VideoService;
import com.xianglan.qnytv.service.entity.req.SaveVideoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 视频管理
 */
@RequestMapping("/videoManager")
@RestController
public class VideoManagerController {


    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucketName}")
    private String bucketName;

    @Value("${qiniu.domainUrl}")
    private String domainUrl;


    @Autowired
    private VideoService videoService;

    /**
     * 获取视频列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getPageList")
    public JsonResponse<Page<VideoPo>> getPageList(@RequestBody GetVideoListRequest request) {
        Page<VideoPo> page = videoService.getPageList(request);
        return new JsonResponse<>(page);
    }

    @RequestMapping("/saveVideo")
    public JsonResponse<Void> saveVideo(@RequestBody SaveVideoRequest po) {
        videoService.saveVideo(po);

        return new JsonResponse<>(null);
    }

    /**
     * 上传视频
     *
     * @param file 文件
     * @return
     */
    @RequestMapping("/upload")
    public JsonResponse<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return JsonResponse.fail();
        }

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);

        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = file.getOriginalFilename();

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucketName);

        try {
            uploadManager.put(file.getBytes(), key, upToken);
            System.out.println("上传文件");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String finalUrl = domainUrl + "/" + key;
        //todo 正式的时候删除以下代码，列表中获取临时的令牌
        finalUrl = auth.privateDownloadUrl(finalUrl, 3600);

        return new JsonResponse<>(finalUrl);
    }
}