package com.roc.demo.upms.api.feign;

import com.roc.demo.common.core.constant.SecurityConstants;
import com.roc.demo.common.core.constant.ServiceNameConstants;
import com.roc.demo.common.core.utils.R;
import com.roc.demo.upms.api.entity.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Description RemoteLogService
 * @Author dongp
 * @Date 2022/6/29 0029 16:50
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteLogService {

    /**
     * 保存日志
     *
     * @param sysLog 日志实体
     * @param from   内部调用标志
     * @return succes、false
     */
    @PostMapping("/log")
    R<Boolean> saveLog(@RequestBody SysLog sysLog, @RequestHeader(SecurityConstants.FROM) String from);
}
