package com.min.auto.api.handler;

import com.min.auto.api.bean.Result;
import com.min.auto.api.enums.ResultEnum;
import com.min.auto.api.exception.ServerException;
import com.min.auto.api.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof ServerException) {
            ServerException serverException = (ServerException) e;
            return ResultUtil.error(serverException.getCode(), serverException.getMessage());
        }else {
            logger.error("系统异常",e);
            return ResultUtil.error(ResultEnum.UNKONW_ERROR.getCode(), ResultEnum.UNKONW_ERROR.getMessage());
        }
    }
}
