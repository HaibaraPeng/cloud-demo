package com.roc.demo.common.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * @Description DemoResourceServerAutoConfiguration
 * @Author dongp
 * @Date 2022/6/29 0029 18:14
 */
@RequiredArgsConstructor
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class DemoResourceServerAutoConfiguration {

    /**
     * 鉴权具体的实现逻辑
     *
     * @return （#pms.xxx）
     */
    @Bean("pms")
    public PermissionService permissionService() {
        return new PermissionService();
    }

    /**
     * 请求令牌的抽取逻辑
     *
     * @param urlProperties 对外暴露的接口列表
     * @return BearerTokenExtractor
     */
    @Bean
    public DemoBearerTokenExtractor demoBearerTokenExtractor(PermitAllUrlProperties urlProperties) {
        return new DemoBearerTokenExtractor(urlProperties);
    }

    /**
     * 资源服务器异常处理
     *
     * @param objectMapper jackson 输出对象
     * @return ResourceAuthExceptionEntryPoint
     */
    @Bean
    public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper) {
        return new ResourceAuthExceptionEntryPoint(objectMapper);
    }

    /**
     * 资源服务器toke内省处理器
     *
     * @param authorizationService token 存储实现
     * @return TokenIntrospector
     */
    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService authorizationService) {
        return new DemoCustomOpaqueTokenIntrospector(authorizationService);
    }
}
