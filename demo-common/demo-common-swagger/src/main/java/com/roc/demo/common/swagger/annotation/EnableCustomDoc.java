package com.roc.demo.common.swagger.annotation;

import com.roc.demo.common.swagger.config.SwaggerAutoConfiguration;
import com.roc.demo.common.swagger.config.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description 开启自定义springDoc
 * @Author penn
 * @Date 2022/6/25 16:10
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({SwaggerAutoConfiguration.class})
public @interface EnableCustomDoc {
}
