package com.roc.modules.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 用户信息
 * @Author dongp
 * @Date 2022/6/24 0024 14:27
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    /**
     * hello world
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
