package com.da.learn.swagger.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() throws InterruptedException {
        long start = System.currentTimeMillis();
        ThreadLocalRandom.current().nextInt(100);
        Random rand = new Random();
        Thread.sleep(rand.nextInt(400) + 100L);
        System.out.println(System.currentTimeMillis() - start);
        return "ok";
    }
}
