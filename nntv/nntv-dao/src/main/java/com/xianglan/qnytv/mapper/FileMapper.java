package com.xianglan.qnytv.mapper;

import com.xianglan.qnytv.domain.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    Integer addFile(File file);

    File getFileByMD5(String md5);
}
