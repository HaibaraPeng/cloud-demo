package com.roc.demo.visual.generate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author dongp
 * @Date 2022/6/30 0030 16:56
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("test")
    public String test() {
        return "helloworld";
    }
}
