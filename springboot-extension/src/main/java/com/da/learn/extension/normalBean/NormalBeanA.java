package com.da.learn.extension.normalBean;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class NormalBeanA implements BeanNameAware, InitializingBean, DisposableBean {
    public NormalBeanA() {
        System.out.println("NormalBeanA constructor");
    }
    @Override
    public void setBeanName(String name) {
        System.out.println("[BeanNameAware] " + name);
    }

    @PostConstruct
    public void init() {
        System.out.println("[PostConstruct] NormalBeanA");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[InitializingBean] NormalBeanA");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("[DisposableBean] NormalBeanA");
    }
}
