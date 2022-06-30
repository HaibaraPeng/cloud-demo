package com.roc.demo.visual.generate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description 代码生成模块
 * @Author dongp
 * @Date 2022/6/30 0030 16:20
 */
//@EnableCustomDoc
//@EnableDynamicDataSource
@EnableDiscoveryClient
@SpringBootApplication
public class DemoGenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoGenerateApplication.class, args);
    }
}
