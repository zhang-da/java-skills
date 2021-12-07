package com.da.learn.activiti.controller;

import com.da.learn.common.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@RestController
@RequestMapping("/test")
@Api(tags = "1.0.0-SNAPSHOT", value = "test")
@Slf4j
public class TestController {

    @Autowired
    private TaskRuntime taskRuntime;

    @GetMapping
    @ApiOperation(value = "test", notes = "test")
    public R test(String test) {
        return R.ok();
    }

    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.of(2019, 9, 19, 10, 50, 8);
        LocalDateTime now = LocalDateTime.of(2019, 10, 25, 11, 14, 8);

        LocalDateTime next = time.plusDays(30).plusDays(20);
        System.out.println(next);
        System.out.println(now);

        int afterSaleDays = (int) ((now.toEpochSecond(ZoneOffset.of("+8")) - time.toEpochSecond(ZoneOffset.of("+8"))) / (3600L * 24));
        System.out.println(afterSaleDays);


        Date nextTime = new Date((time.toEpochSecond(ZoneOffset.of("+8")) + 50L * 24 * 60 * 60)*1000L);
        System.out.println(nextTime);

        double remainDays = (next.toEpochSecond(ZoneOffset.of("+8")) - now.toEpochSecond(ZoneOffset.of("+8"))) / (3600L * 24.0);
        System.out.println(remainDays);
    }
}
