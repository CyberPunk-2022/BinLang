package com.xianglan.qnytv.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors
@TableName("t_user_info")
public class UserInfo {
    @TableId
    private Long id;
    @TableField(value = "userId")
    private Long userId;

    @TableField(value = "nick")
    private String nick;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "sign")
    private String sign;

    @TableField(value = "gender")
    private String gender;

    @TableField(value = "birth")
    private String birth;

    @TableField(value = "createTime")
    private Date createTime;

    @TableField(value = "updateTime")
    private Date updateTime;
}
