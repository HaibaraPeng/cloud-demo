package com.roc.demo.common.security.service;

import cn.hutool.system.UserInfo;
import com.roc.demo.common.core.utils.R;
import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description DemoUserDetailsService
 * @Author dongp
 * @Date 2022/6/30 0030 14:57
 */
public interface DemoUserDetailsService extends UserDetailsService, Ordered {

    /**
     * 是否支持此客户端校验
     *
     * @param clientId  目标客户端
     * @param grantType
     * @return true/false
     */
    default boolean support(String clientId, String grantType) {
        return true;
    }

    /**
     * 排序值 默认取最大的
     *
     * @return 排序值
     */
    @Override
    default int getOrder() {
        return 0;
    }

}
