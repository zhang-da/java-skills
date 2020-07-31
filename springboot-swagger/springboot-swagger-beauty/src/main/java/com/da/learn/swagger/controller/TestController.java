package com.da.learn.swagger.controller;

import com.da.learn.swagger.bean.ApiResponse;
import com.da.learn.swagger.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(tags = "1.0.0-SNAPSHOT", value = "test")
@Slf4j
public class TestController {
    @GetMapping
    @ApiOperation(value = "test（DONE）", notes = "test")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "header", defaultValue = "xxx")})
    public ApiResponse<User> getByUserName(@RequestParam String name) {
        return ApiResponse.<User>builder().code(200)
                .message("success")
                .data(new User(1L, name))
                .build();
    }
}
