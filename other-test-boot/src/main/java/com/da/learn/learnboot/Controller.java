package com.da.learn.learnboot;

import com.alibaba.excel.EasyExcel;
import com.da.learn.learnboot.maintainpush.excel.MaintainItemRepository;
import com.da.learn.learnboot.maintainpush.excel.NoModelDataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private MaintainItemRepository maintainItemRepository;

    @RequestMapping("/excel")
    public String test() {
        String fileName = "D:\\迭代\\V2.26\\长春保养提醒（20200521）.xlsx";
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, new NoModelDataListener(maintainItemRepository)).sheet(1).headRowNumber(3).doRead();
        return "ok";
    }
}
