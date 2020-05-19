package com.da.learn.learnboot.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringEventTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.da.learn.learnboot");
//        applicationContext.refresh();
        MessageService messageService = (MessageService) applicationContext.getBean("messageService");
        messageService.sendMessage("1574480311");
    }

}
