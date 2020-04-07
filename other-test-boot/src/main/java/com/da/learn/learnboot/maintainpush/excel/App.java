package com.da.learn.learnboot.maintainpush.excel;

import com.alibaba.excel.EasyExcel;

public class App {

    public static void main(String[] args) {
        String fileName = "D:\\迭代\\V2.26\\长春J7保养提醒new.xlsx";
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
//        EasyExcel.read(fileName, new NoModelDataListener()).sheet(1).headRowNumber(2).doRead();

    }
}
