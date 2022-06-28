package com.roc.demo.modules.generate.utils;

import cn.hutool.core.util.CharsetUtil;
import com.roc.demo.common.core.constant.Constants;
import org.apache.velocity.app.Velocity;

import java.util.Properties;

/**
 * @Description VelocityEngine工厂
 * @Author dongp
 * @Date 2022/6/28 0028 14:41
 */
public class VelocityInitializer {

    /**
     * 初始化vm方法
     */
    public static void initVelocity() {
        Properties p = new Properties();
        try {
            // 加载classpath目录下的vm文件
            p.setProperty("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 定义字符集
            p.setProperty(Velocity.INPUT_ENCODING, CharsetUtil.UTF_8);
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
