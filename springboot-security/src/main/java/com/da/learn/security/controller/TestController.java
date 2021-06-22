package com.da.learn.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("hello")
    public String hello() {
        return "hello security";
    }


    @GetMapping("index")
    public String index() {
        return "hello index";
    }

    @GetMapping("update")
//    @Secured({"ROLE_role1"})
//    @PreAuthorize("hasAnyAuthority('admins')")
//    @PostAuthorize("hasAuthority('adminss')")
    public String update() {
        System.out.println("updating.....");
        return "hello update";
    }
}
