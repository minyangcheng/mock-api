package com.min.auto.api.util;

import com.min.auto.api.bean.Result;
import com.min.auto.api.enums.ResultEnum;

public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode())
                .setMessage(ResultEnum.SUCCESS.getMessage())
                .setData(object);
        return result;
    }

    public static Result success(String message,Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode())
                .setMessage(message)
                .setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error() {
        Result result = new Result();
        result.setCode(ResultEnum.FAIL.getCode())
                .setMessage(ResultEnum.FAIL.getMessage());
        return result;
    }

    public static Result error(int code, String message) {
        Result result = new Result();
        result.setCode(code)
                .setMessage(message);
        return result;
    }
}
