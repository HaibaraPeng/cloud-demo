package com.roc.demo.modules.system;

import com.roc.demo.common.swagger.annotation.EnableCustomDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description 系统模块
 * @Author dongp
 * @Date 2022/6/24 0024 12:34
 */
@EnableCustomDoc
@SpringBootApplication
public class DemoSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoSystemApplication.class, args);
    }
}
