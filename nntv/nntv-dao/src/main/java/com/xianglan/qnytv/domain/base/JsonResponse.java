package com.xianglan.qnytv.domain.base;

import com.xianglan.qnytv.domain.constant.StatusEnum;
import lombok.Getter;

@Getter
public class JsonResponse<T> {

    private String code;

    private String msg;

    private T data;

    public JsonResponse() {

    }

    public JsonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(T data) {
        this.data = data;
        msg = "成功";
        code = StatusEnum.SUCCESS.getCode();
    }

    public static JsonResponse<String> success() {
        return new JsonResponse<>(null);
    }

    public static JsonResponse<String> success(String data) {
        return new JsonResponse<>(data);
    }

    public static JsonResponse<String> fail() {
        return new JsonResponse<>(StatusEnum.FAIL.getCode(), "失败");
    }

    public static JsonResponse<String> fail(String code, String msg) {
        return new JsonResponse<>(code, msg);
    }

    public static JsonResponse<String> fail(String errorMsg) {
        return new JsonResponse<>(StatusEnum.FAIL.getCode(), errorMsg);
    }

    public static JsonResponse<Object> success(Object data) {
        JsonResponse<Object> response = new JsonResponse<>();
        response.setCode(StatusEnum.SUCCESS.getCode());
        response.setMsg(StatusEnum.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
