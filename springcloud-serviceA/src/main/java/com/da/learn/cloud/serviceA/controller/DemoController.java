package com.da.learn.cloud.serviceA.controller;

import com.da.learn.cloud.serviceA.feign.ServiceBFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private ServiceBFeign serviceBFeign;

    @GetMapping("getInfo")
    public String getInfo() {
        return serviceBFeign.getInfo();
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }
}
