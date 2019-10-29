package com.da.learn.learnboot.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener implements ApplicationListener {

    public MessageListener() {
        System.out.println("hello");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("==================hahahahaha" + event.toString());
        if (event instanceof MessageEvent) {
            String phoneNumber = (String) event.getSource();
            System.out.println("我已收到通知：即将向" + phoneNumber + "发短信了...");
        }
    }
}
