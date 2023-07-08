package com.blog.server.handler;

import com.blog.common.resopnse.ResponseResult;
import com.blog.server.exceptions.AuthorizationException;
import com.blog.server.exceptions.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yanzhihao
 * @since 2023/4/28
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 以下是系统的异常
     *
     * @return 异常的提醒信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<Void> error(Exception e) {
        e.printStackTrace();
        return ResponseResult.fail(500, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseResult<Void> error(IllegalArgumentException e) {
        e.printStackTrace();
        return ResponseResult.fail(500, e.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public ResponseResult<Void> error(AuthorizationException e) {
        e.printStackTrace();
        return ResponseResult.fail(401, "没有权限");
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseResult<Void> error(ServiceException e) {
        e.printStackTrace();
        return ResponseResult.fail(500, e.getMessage());
    }
}