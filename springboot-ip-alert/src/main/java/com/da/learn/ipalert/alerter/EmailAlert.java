package com.da.learn.ipalert.alerter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("emailAlert")
public class EmailAlert implements Alert {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    public void execute(String ip) {
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者
        mainMessage.setFrom(mailProperties.getFrom());
        //接收者
        mainMessage.setTo(mailProperties.getTo());
        //发送的标题
        mainMessage.setSubject(mailProperties.getSubject());
        //发送的内容
        mainMessage.setText(ip);
        javaMailSender.send(mainMessage);
    }
}
