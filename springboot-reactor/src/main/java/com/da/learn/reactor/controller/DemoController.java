package com.da.learn.reactor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
public class DemoController {
    /**
     * webflux 兼容webmvc
     * @return
     */

    @GetMapping("/test_mvc")
    public String testMvc() throws InterruptedException {
        long start = System.currentTimeMillis();
        Random rand = new Random();
        Thread.sleep(rand.nextInt(400) + 100L);
        System.out.println(System.currentTimeMillis() - start);
        return "test";
    }

    @GetMapping("/list_mvc")
    public List<Integer> listMvc() {
        return Arrays.asList(1, 2, 3);
    }

    @GetMapping("/test")
    public Mono<String> test() throws InterruptedException {
        return Mono.just("test");
    }

    @GetMapping("/list")
    public Flux<Integer> list() {
        return Flux.just(1, 2, 3);
    }
}
