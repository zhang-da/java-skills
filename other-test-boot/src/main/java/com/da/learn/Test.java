package com.da.learn;

public class Test implements Cloneable {
    private int anInt;

    public static void main(String[] args) throws Exception {
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if ("main".equals(stackTraceElement.getMethodName())) {
                System.out.println("test");
            }
        }
    }


}
