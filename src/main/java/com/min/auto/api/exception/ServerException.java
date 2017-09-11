package com.min.auto.api.exception;

import com.min.auto.api.enums.ResultEnum;

public class ServerException extends RuntimeException{

    private int code;

    public ServerException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ServerException(int code,String message){
        super(message);
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
