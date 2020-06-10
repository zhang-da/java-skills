package com.da.learn.javafx.module;

import com.da.learn.javafx.service.LoginService;
import com.jfoenix.controls.*;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
@Slf4j
public class LoginController implements Initializable {

    @Autowired
    private LoginService loginService;

    @FXML
    public JFXButton btn_start;
    @FXML
    public JFXTextField tf_user;
    @FXML
    public JFXPasswordField tf_passWord;
    @FXML
    public JFXProgressBar prgs_login;
    @FXML
    public JFXCheckBox rememberInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("登录页面加载");
    }

    /**
     * 登录按钮点击事件
     */
    @FXML
    public void onStart(ActionEvent actionEvent) {
        log.info("点登录");
        loginService.login("testUserName", "testPassword");
    }
}
