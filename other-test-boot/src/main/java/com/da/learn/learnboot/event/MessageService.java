package com.da.learn.learnboot.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("messageService")
public class MessageService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void sendMessage(String phoneNumber)
    {
        MessageEvent evt = new MessageEvent(phoneNumber);
        // 发布事件
        applicationContext.publishEvent(evt);
    }
}
