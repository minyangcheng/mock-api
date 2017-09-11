package com.min.auto.api.enums;

public enum ResultEnum {

    UNKONW_ERROR(30000, "未知错误"),
    SUCCESS(10000, "请求成功"),
    FAIL(20000, "请求失败");

    private int code;

    private String message;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
