package com.roc.demo.modules.generate;

import com.roc.demo.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description DemoGenerateApplication
 * @Author penn
 * @Date 2022/6/26 10:17
 */
@EnableCustomSwagger2
@SpringBootApplication
public class DemoGenerateApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoGenerateApplication.class, args);
    }
}
