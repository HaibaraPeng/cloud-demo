package com.roc.demo.common.security.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;

/**
 * @Description 资源服务注解
 * @Author dongp
 * @Date 2022/6/29 0029 18:13
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Import({PigResourceServerAutoConfiguration.class, PigResourceServerConfiguration.class,
//        PigFeignClientConfiguration.class})
public @interface EnablePigResourceServer {
}
