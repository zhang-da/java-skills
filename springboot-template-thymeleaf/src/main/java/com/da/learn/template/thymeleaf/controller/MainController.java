package com.da.learn.template.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
    public String home(HttpServletRequest request) {
//        log.info("home page");
//        if (!SecurityUtils.getSubject().isAuthenticated() || Userinfo.getUser() == null) {
//            request.setAttribute("kaptcha", linProperties.isKptchaswich());
//            return "login2";
//        }
//
//        //右侧的通知
//        List<ShowNotice> list = noticeService.getAllShowNotice();
//        request.setAttribute("noticelist", list);
//
//        List<Menu> menuList = menuService.getUserMenu();
//        request.setAttribute("menulist", menuList);
//
//        request.setAttribute("username", Userinfo.getUsername());
//        request.setAttribute("tip", ((User) SecurityUtils.getSubject().getPrincipal()).getRoleTip());
//        request.setAttribute("sex", Userinfo.getSex());
//        return "index1";

        return "login";

    }
}
