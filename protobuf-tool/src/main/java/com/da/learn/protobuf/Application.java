package com.da.learn.protobuf;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        double count = list.size();
        int carListSize = list.size();
        //1万条一查位置云
        int pages = (int) Math.ceil(count / 2);
        List<Integer> subList = new ArrayList<>();
        for (int i = 0; i < pages; i++) {
            if (i + 1 == pages) {
                if (carListSize % 2 == 0) {
                    subList = list.subList(i * 2, i * 2 + 2);
                } else {
                    subList = list.subList(i * 2, i * 2 + (carListSize % 2));
                }
            } else {
                subList = list.subList(i * 2, 2 * (i + 1));
//                if (i == 0) {
//                } else {
//                    subList = list.subList(i * 2, 2 * (i + 1));
//                }
            }
            System.out.println("======");
            System.out.println(subList);
        }
    }
}
