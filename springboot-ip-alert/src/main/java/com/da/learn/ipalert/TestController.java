package com.da.learn.ipalert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @RequestMapping(value = "hello")
    public String test() {
        log.info("hello");
        return "success";
    }
}
