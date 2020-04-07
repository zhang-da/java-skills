package com.da.learn.learnboot.spi;

import java.util.ServiceLoader;

public class App {
    public static void main(String[] args) {
        Integer a = 0x7FFFFFFF;
        System.out.println(a);
        ServiceLoader<SpiDemoInterface> loaders = ServiceLoader.load(SpiDemoInterface.class);
        loaders.forEach(SpiDemoInterface::test);
    }
}
