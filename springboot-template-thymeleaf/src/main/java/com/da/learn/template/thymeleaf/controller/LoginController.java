package com.da.learn.template.thymeleaf.controller;

import com.da.learn.template.thymeleaf.bean.URL;
import com.da.learn.template.thymeleaf.config.ProjectProperties;
import com.da.learn.template.thymeleaf.exception.AirException;
import com.da.learn.template.thymeleaf.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController {

    @Autowired
    private ProjectProperties projectProperties;


    /**
     * 跳转到登录页面
     */
    @GetMapping("/login")
    public String toLogin(Model model) {
        model.addAttribute("isCaptcha", projectProperties.isCaptchaOpen());
        return "/login";
    }

    /**
     * 实现登录
     */
    @PostMapping("/login")
    @ResponseBody
    public Result login(String username, String password, String captcha, String rememberMe) {
        // 判断账号密码是否为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new AirException(1001, "用户名或密码不能为空");
        }

        // 判断验证码是否正确
        if (projectProperties.isCaptchaOpen()) {
            //...
        }

        if (!"admin".equals(username) || !"admin".equals(password)) {
            return Result.failed("用户名或密码错误");
        }

        return Result.ok(new URL("/"), "登录成功");
    }
}
