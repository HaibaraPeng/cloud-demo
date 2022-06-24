package com.roc.demo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Description 认证授权中心
 * @Author dongp
 * @Date 2022/6/24 0024 17:44
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoAuthApplication.class, args);
    }
}
