package com.xianglan.qnytv.domain.constant;

import lombok.Data;
import lombok.Getter;

/**
 * 状态枚举
 */
@Getter
public enum StatusEnum {

    SUCCESS("000000", "成功"),

    FAIL("999999", "失败"),

    AUTH_FAIL("555555", "授权失败");


    private String code;


    private String msg;

    StatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}