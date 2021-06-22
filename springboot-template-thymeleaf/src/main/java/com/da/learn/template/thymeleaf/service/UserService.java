package com.da.learn.template.thymeleaf.service;

import com.da.learn.template.thymeleaf.bean.User;
import com.da.learn.template.thymeleaf.shiro.ShiroUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private List<User> userTestList = new ArrayList<>();

    {
        String salt = ShiroUtil.getRandomSalt();
        userTestList.add(new User(1L, "admin", ShiroUtil.encrypt("admin", salt), salt, 0));
        userTestList.add(new User(2L, "test1", ShiroUtil.encrypt("test1", salt), salt, 0));
        userTestList.add(new User(3L, "test2", ShiroUtil.encrypt("test2", salt), salt, 1));
    }

    public User getByName(String username) {
        for (User user : userTestList) {
            if (Objects.equals(username, user.getUsername())) {
                return user;
            }
        }
        return null;
    }
}
