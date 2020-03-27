package com.da.learn.learnboot.circledepedency;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.da.learn.learnboot.circledepedency");
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        Stream.of(beanDefinitionNames).forEach(System.out::println);
    }


}
