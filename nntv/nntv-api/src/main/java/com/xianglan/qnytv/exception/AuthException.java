package com.xianglan.qnytv.exception;

public class AuthException extends RuntimeException {


    private String code;//错误码


    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String code, String message) {
        super(message);
        this.code = code;
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getCode() {
        return code;
    }

}
