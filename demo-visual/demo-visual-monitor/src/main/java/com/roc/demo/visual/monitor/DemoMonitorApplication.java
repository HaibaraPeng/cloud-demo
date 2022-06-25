package com.roc.demo.visual.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description DemoMonitorApplication
 * @Author penn
 * @Date 2022/6/25 14:54
 */
@EnableAdminServer
@SpringBootApplication
public class DemoMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoMonitorApplication.class, args);
    }
}
