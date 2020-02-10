package com.da.learn.excel.repository;

import com.da.learn.excel.bean.UploadData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UploadDAO {
    public void saveData(List<UploadData> list) {
        log.info("{}条数据，开始存储数据库！", list.size());
        // TODO: 2020/1/21 save
        log.info("存储数据库成功！");
    }
}
