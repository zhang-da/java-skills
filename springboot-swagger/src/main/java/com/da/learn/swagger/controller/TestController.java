package com.da.learn.swagger.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() throws InterruptedException {
        long start = System.currentTimeMillis();
        Random rand = new Random();
        Thread.sleep(rand.nextInt(400) + 100L);
        System.out.println(System.currentTimeMillis() - start);
        return "ok";
    }
}
