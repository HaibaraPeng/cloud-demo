package com.roc.demo.common.log.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Description TODO
 * @Author dongp
 * @Date 2022/6/29 0029 17:32
 */
@Getter
@RequiredArgsConstructor
public enum LogTypeEnum {
    /**
     * 正常日志类型
     */
    NORMAL("0", "正常日志"),

    /**
     * 错误日志类型
     */
    ERROR("9", "错误日志");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String description;
}
