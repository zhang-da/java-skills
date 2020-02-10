package com.da.learn.excel.bean;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionDemoData {
    /**
     * 用日期去接字符串 肯定报错
     */
    private Date date;
}
