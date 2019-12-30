package com.da.learn.learnboot.listpage;

import org.springframework.util.ObjectUtils;

import java.util.*;

public class ListPage {

    private static final int ONE_PAGE = 100;

    public static void main(String[] args) {

    }

    private void deleteRecord(List<String> list) {
        if (ObjectUtils.isEmpty(list)) {
            return;
        }
        List<String> subList;
        for (int i = 0; i < list.size(); i += ONE_PAGE) {
            int end = i + ONE_PAGE > list.size() ? list.size() : i + ONE_PAGE;
            subList = list.subList(i, end);
            System.out.println("==pages:" + i + "==");
            //调用删除
            doSomething(subList);
        }
    }

    private void doSomething(List<String> list) {
        System.out.println("do: " + list);
    }
}
