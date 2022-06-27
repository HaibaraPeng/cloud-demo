package com.roc.demo.common.core.api;

import lombok.Getter;

/**
 * @Description CommonResult
 * @Author dongp
 * @Date 2022/4/19 0019 17:27
 */
@Getter
public class CommonResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public CommonResult code(Integer code) {
        this.code = code;
        return this;
    }

    public CommonResult message(String message) {
        this.message = message;
        return this;
    }

    public CommonResult<T> data(T data) {
        this.data = data;
        return this;
    }

    public CommonResult success() {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
        return this;
    }

    public CommonResult validateFailed() {
        return validateFailed(ResultCode.VALIDATE_FAILED.getMessage());
    }

    public CommonResult validateFailed(String message) {
        this.code = ResultCode.VALIDATE_FAILED.getCode();
        this.message = message;
        return this;
    }

    public CommonResult unauthorized() {
        this.code = ResultCode.UNAUTHORIZED.getCode();
        this.message = ResultCode.UNAUTHORIZED.getMessage();
        return this;
    }

    public CommonResult forbidden() {
        this.code = ResultCode.FORBIDDEN.getCode();
        this.message = ResultCode.FORBIDDEN.getMessage();
        return this;
    }

    public CommonResult failed() {
        this.code = ResultCode.FAILED.getCode();
        this.message = ResultCode.FAILED.getMessage();
        return this;
    }

}
