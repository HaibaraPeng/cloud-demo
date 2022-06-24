package com.roc.demo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Description 网关启动程序
 * @Author dongp
 * @Date 2022/6/24 0024 17:00
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoGatewayApplication.class, args);
    }
}
