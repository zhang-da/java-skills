package com.da.learn.learnboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("/**")
    public String test() {
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        System.out.println(stackTrace);

        return "ok";
    }
}
