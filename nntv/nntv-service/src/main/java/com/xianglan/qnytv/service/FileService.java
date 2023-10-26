package com.xianglan.qnytv.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.xianglan.qnytv.service.util.MD5Util;
@Service
public class FileService {
    public String getFileMD5(MultipartFile file) throws Exception {
        return MD5Util.getFileMD5(file);
    }
}
