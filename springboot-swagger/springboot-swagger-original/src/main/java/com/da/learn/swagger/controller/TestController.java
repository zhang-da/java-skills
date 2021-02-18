package com.da.learn.swagger.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(@RequestParam(name = "id", required = false) String id, @RequestParam(name = "phone", required = false) String phone) throws InterruptedException {
        System.out.println(id);
        System.out.println(phone);
//        long start = System.currentTimeMillis();
//        ThreadLocalRandom.current().nextInt(100);
//        Random rand = new Random();
//        Thread.sleep(rand.nextInt(400) + 100L);
//        System.out.println(System.currentTimeMillis() - start);
        return "ok";
    }
}
