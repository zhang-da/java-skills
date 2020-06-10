package com.da.learn.javafx.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService {
    public boolean login(String userName, String password) {
        log.info(userName);
        log.info(password);
        return true;
    }
}
