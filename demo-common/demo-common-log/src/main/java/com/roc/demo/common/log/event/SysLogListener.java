package com.roc.demo.common.log.event;

import com.roc.demo.common.core.constant.SecurityConstants;
import com.roc.demo.upms.api.entity.SysLog;
import com.roc.demo.upms.api.feign.RemoteLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @Description 异步监听日志事件
 * @Author dongp
 * @Date 2022/6/29 0029 17:51
 */
@Slf4j
@RequiredArgsConstructor
public class SysLogListener {

    private final RemoteLogService remoteLogService;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLog sysLog = (SysLog) event.getSource();
        remoteLogService.saveLog(sysLog, SecurityConstants.FROM_IN);
    }
}
