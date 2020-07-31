package com.da.learn.swagger.controller;

import com.da.learn.swagger.bean.ApiResponse;
import com.da.learn.swagger.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/swagger_test")
@Api(tags = "1.0.0-SNAPSHOT", value = "测试swagger")
@Slf4j
public class SwaggerTestController {

    @GetMapping
    @ApiOperation(value = "条件查询（DONE）", notes = "备注")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "header", defaultValue = "xxx")})
    public ApiResponse<User> getByUserName(@RequestParam String name) {
        return ApiResponse.<User>builder().code(200)
                .message("success")
                .data(new User(1L, name))
                .build();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询（DONE）", notes = "备注")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "header", defaultValue = "xxx")})
    public ApiResponse<User> get(@PathVariable Long id) {
        return ApiResponse.<User>builder().code(200)
                .message("success")
                .data(new User(id, "u1"))
                .build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户（DONE）", notes = "备注")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "header", defaultValue = "xxx")})
    public ApiResponse delete(@PathVariable Long id) {
        return ApiResponse.builder().code(200).message("success").build();
    }

    @PostMapping
    @ApiOperation(value = "添加用户（DONE）")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "header", defaultValue = "xxx")})
    public ApiResponse<User> post(@RequestBody User user) {
        return ApiResponse.<User>builder().code(200).message("success").data(user).build();
    }

    @PostMapping("/multipar")
    @ApiOperation(value = "添加用户（DONE）")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "header", defaultValue = "xxx")})
    public ApiResponse<List<User>> multipar(@RequestBody List<User> userList) {
        return ApiResponse.<List<User>>builder().code(200).message("success").data(userList).build();
    }

    @PostMapping("/array")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "header", defaultValue = "xxx")})
    @ApiOperation(value = "添加用户（DONE）")
    public ApiResponse<User[]> array(@RequestBody User[] users) {
        return ApiResponse.<User[]>builder().code(200).message("success").data(users).build();
    }

    @PutMapping("/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "header", defaultValue = "xxx")})
    @ApiOperation(value = "修改用户（DONE）")
    public ApiResponse put(@PathVariable Long id, @RequestBody User user) {
        return ApiResponse.builder().code(200).message("success").build();
    }

    @PostMapping("/{id}/file")
    @ApiOperation(value = "文件上传（DONE）")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "header", defaultValue = "xxx")})
    public ApiResponse<String> file(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        log.info(file.getContentType());
        log.info(file.getName());
        log.info(file.getOriginalFilename());
        return ApiResponse.<String>builder().code(200).message("success").data(file.getOriginalFilename()).build();
    }

}
