package com.blog.common.resopnse;

public class ResponseResult<T> {

    private Integer code;

    private Boolean success;

    private String message;

    private T data;

    public static <T> ResponseResult<T> success(T data) {

        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.code = 200;
        responseResult.success = true;
        responseResult.message = "success";
        responseResult.data = data;

        return responseResult;
    }

    public static <T> ResponseResult<T> success() {

        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.code = 200;
        responseResult.success = true;
        responseResult.message = "success";
        responseResult.data = null;

        return responseResult;
    }

    public static <T> ResponseResult<T> fail(T data) {

        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.code = 200;
        responseResult.success = false;
        responseResult.message = "fail";
        responseResult.data = data;

        return responseResult;
    }

    public static <T> ResponseResult<T> fail() {

        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.code = 200;
        responseResult.success = false;
        responseResult.message = "fail";
        responseResult.data = null;

        return responseResult;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
