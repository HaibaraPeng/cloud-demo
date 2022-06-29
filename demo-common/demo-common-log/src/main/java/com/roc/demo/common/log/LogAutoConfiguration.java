package com.roc.demo.common.log;

import com.roc.demo.common.log.aspect.SysLogAspect;
import com.roc.demo.common.log.event.SysLogListener;
import com.roc.demo.upms.api.feign.RemoteLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Description 日志自动配置
 * @Author dongp
 * @Date 2022/6/29 0029 16:35
 */
@EnableAsync
@RequiredArgsConstructor
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {

    @Bean
    public SysLogListener sysLogListener(RemoteLogService remoteLogService) {
        return new SysLogListener(remoteLogService);
    }

    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }
}
