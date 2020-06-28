package com.da.learn.cloud.prometheus.controller;

import com.da.learn.cloud.prometheus.aspect.MethodMetric;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @MethodMetric
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
}
