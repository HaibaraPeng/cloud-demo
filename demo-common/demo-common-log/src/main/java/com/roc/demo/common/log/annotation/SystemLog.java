package com.roc.demo.common.log.annotation;

import java.lang.annotation.*;

/**
 * @Description 操作日志注解
 * @Author dongp
 * @Date 2022/6/29 0029 17:27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    /**
     * 描述
     *
     * @return {String}
     */
    String value() default "";

    /**
     * spel 表达式
     *
     * @return 日志描述
     */
    String expression() default "";
}
