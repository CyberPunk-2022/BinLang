package com.xianglan.qnytv.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_file")
public class File {
    private Long id;

    private String url;

    private String type;

    private String md5;

    private Date createTime;
}
