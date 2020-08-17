package com.da.learn.learnboot.useenum;

public enum SingletonEnum {
    INSTANCE;

    public void doSomething(){
        // dosomething...
        System.out.println("do something...");
    }

    public static void main(String[] args) {
        SingletonEnum.INSTANCE.doSomething();
    }
}
