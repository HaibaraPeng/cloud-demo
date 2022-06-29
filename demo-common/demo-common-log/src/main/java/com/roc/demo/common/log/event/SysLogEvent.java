package com.roc.demo.common.log.event;

import com.roc.demo.upms.api.entity.SysLog;
import org.springframework.context.ApplicationEvent;

/**
 * @Description 系统日志事件
 * @Author dongp
 * @Date 2022/6/29 0029 17:49
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(SysLog source) {
        super(source);
    }
}
