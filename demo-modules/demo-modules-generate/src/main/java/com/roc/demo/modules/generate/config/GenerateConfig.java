package com.roc.demo.modules.generate.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 代码生成相关配置
 * @Author dongp
 * @Date 2022/6/27 0027 16:54
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "generate")
public class GenerateConfig {

    /**
     * 作者
     */
    private String author;

    /**
     * 生成包路径
     */
    private String packageName;

    /**
     * 自动去除表前缀，默认是false
     */
    private Boolean autoRemovePre;

    /**
     * 表前缀(类名不会包含表前缀)
     */
    private String tablePrefix;
}
