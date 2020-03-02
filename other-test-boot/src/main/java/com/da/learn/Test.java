package com.da.learn;

public class Test implements Cloneable {
    private int anInt;

    public static void main(String[] args) throws Exception {
        Test t = new Test();
        System.out.println(t.clone());
    }


}
