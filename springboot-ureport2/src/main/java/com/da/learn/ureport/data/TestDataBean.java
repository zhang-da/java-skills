package com.da.learn.ureport.data;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("testDataBean")
public class TestDataBean {

    public List<Map<String,Object>> loadReportData(String dsName, String datasetName, Map<String,Object> parameters){
        int salary=0;
        Object s=parameters.get("salary");
        if(s!=null){
            salary=Integer.valueOf(salary);
        }
        System.out.println(s);
        List<Map<String,Object>> list=new ArrayList<>();
        for(int i=0;i<20;i++){
            int ss= RandomUtils.nextInt()+i;
//            if(ss<=salary){
//                continue;
//            }
            Map<String,Object> m=new HashMap<>();
            m.put("id", i);
            m.put("name", RandomStringUtils.random(10, true, false));
            m.put("salary", i);
            list.add(m);
        }
        return list;
    }
}
