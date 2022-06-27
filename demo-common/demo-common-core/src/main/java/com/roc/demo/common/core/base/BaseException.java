package com.roc.demo.common.core.base;

import com.roc.demo.common.core.api.ResultCode;

/**
 * @Description 基础异常
 * @Author dongp
 * @Date 2022/4/20 0020 11:37
 */
public class BaseException extends RuntimeException {
    private ResultCode resultCode;

    public BaseException(String message) {
        super(message);
        resultCode = ResultCode.FAILED;
    }

}
