package com.xianglan.qnytv.controller;


import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.xianglan.qnytv.aspect.SkipAuth;
import com.xianglan.qnytv.domain.UserInfo;
import com.xianglan.qnytv.domain.VideoPo;
import com.xianglan.qnytv.domain.base.JsonResponse;
import com.xianglan.qnytv.domain.constant.StatusEnum;
import com.xianglan.qnytv.domain.exception.ConditionException;
import com.xianglan.qnytv.domain.vo.VideoVO;
import com.xianglan.qnytv.mapper.CommentMapper;
import com.xianglan.qnytv.mapper.UserInfoMapper;
import com.xianglan.qnytv.service.UserService;
import com.xianglan.qnytv.service.entity.req.GetVideoListRequest;
import com.xianglan.qnytv.service.VideoService;
import com.xianglan.qnytv.service.entity.req.LikeVideoReq;
import com.xianglan.qnytv.service.entity.req.SaveVideoRequest;
import com.xianglan.qnytv.support.UserSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * 视频管理
 */
@RequestMapping("/videoManager")
@RestController
@Slf4j
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

    @Autowired
    private UserSupport support;


    /**
     * 获取视频列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getPageList")
    @SkipAuth
    public JsonResponse<Page<VideoVO>> getPageList(@RequestBody GetVideoListRequest request) {
        Page<VideoVO> page = videoService.getPageList(request);
        return new JsonResponse<>(page);
    }

    /**
     * 获取视频列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getAuthorPageList")
    @SkipAuth
    public JsonResponse<Page<VideoVO>> getAuthorPageList(@RequestBody GetVideoListRequest request) {
        Page<VideoVO> page = videoService.getPageList(request);
        return new JsonResponse<>(page);
    }

    @RequestMapping("/saveVideo")
    public JsonResponse<Void> saveVideo(@RequestBody SaveVideoRequest po) {
        po.setUserId(support.getCurrentUseUserId());

        videoService.saveVideo(po);
        return new JsonResponse<>(null);
    }


    @RequestMapping("/like")
    public JsonResponse<VideoPo> like(@RequestBody LikeVideoReq req) {
        Long currentUseUserId = support.getCurrentUseUserId();
        req.setUserId(currentUseUserId.toString());
        VideoPo po = videoService.likeVideo(req);
        return new JsonResponse<>(po);
    }

    /**
     * 上传视频
     *
     * @param file 文件
     * @return
     */
    @RequestMapping("/upload")
    @SkipAuth
    public JsonResponse<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return JsonResponse.fail();
        }

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        cfg.resumableUploadAPIV2BlockSize = 1024 * 1024; //1MB开始分片
        cfg.resumableUploadMaxConcurrentTaskCount = 10; //十个并发

        UploadManager uploadManager = new UploadManager(cfg);

        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = file.getOriginalFilename();

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucketName);

        try {

            //创建临时文件夹
            File tempFile = FileUtil.createTempFile();
            //删除临时文件里所有文件
            FileUtil.del(tempFile);
            String path = tempFile.getPath() + File.separator + key;
            byte[] fileBytes = file.getBytes();
            //把fileBytes存入到临时文件夹
            FileUtil.writeBytes(fileBytes, path);

            uploadManager.put(path, key, upToken);
            log.info("上传文件");
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new ConditionException(StatusEnum.FAIL.getCode(), StatusEnum.FAIL.getMsg());
        }
        String finalUrl = domainUrl + "/" + key;
        //todo 正式的时候删除以下代码，列表中获取临时的令牌
        finalUrl = auth.privateDownloadUrl(finalUrl, 3600);

        return new JsonResponse<>(finalUrl);
    }
}
