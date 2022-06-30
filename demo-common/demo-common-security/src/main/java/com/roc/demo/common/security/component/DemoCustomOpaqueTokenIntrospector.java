package com.roc.demo.common.security.component;

import cn.hutool.extra.spring.SpringUtil;
import com.roc.demo.common.security.service.DemoUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.util.Map;

/**
 * @Description DemoCustomOpaqueTokenIntrospector
 * @Author dongp
 * @Date 2022/6/30 0030 14:53
 */
@Slf4j
@RequiredArgsConstructor
public class DemoCustomOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final OAuth2AuthorizationService authorizationService;


    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2Authorization oldAuthorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);

        Map<String, DemoUserDetailsService> userDetailsServiceMap =
                SpringUtil.getBeansOfType(DemoUserDetailsService.class);

        return null;
    }
}
