package com.roc.demo.common.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description ResultCode
 * @Author dongp
 * @Date 2022/4/19 0019 17:37
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(400, "参数检验失败"),

    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),

    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限"),

    /**
     * 操作失败
     */
    FAILED(500, "操作失败");

    /**
     * 返回code
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

}
