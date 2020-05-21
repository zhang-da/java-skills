package com.da.learn.learnboot.maintainpush.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 直接用map接收数据
 */
@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

    private List<Map<Integer, String>> headMapList = new ArrayList<>();
    private List<Map<Integer, String>> resultList = new ArrayList<Map<Integer, String>>();


    private MaintainItemRepository maintainItemRepository;

    public NoModelDataListener(MaintainItemRepository maintainItemRepository) {
        this.maintainItemRepository = maintainItemRepository;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", data);
        List<MaintainItem> maintainItemList = new ArrayList<>();
        String modelCode = data.get(0);
        for (int i = 1; i <= 24; i++) {
            MaintainItem maintainItem = new MaintainItem();
            String value = data.get(i);
            String[] splitValue = value.split("/");
            maintainItem.setModelCode(modelCode);
            maintainItem.setItemCode(i);
            String itemName = headMapList.get(1).get(i);
//            maintainItem.setItemName(itemName.substring(0, itemName.indexOf("（")));
            maintainItem.setItemName(itemName);
            if (i == 1 || i == 2 || i == 3) {
                maintainItem.setTypeCode(1);
                maintainItem.setTypeName(headMapList.get(0).get(1));
            } else if (i == 4 || i == 5 || i == 6) {
                maintainItem.setTypeCode(4);
                maintainItem.setTypeName(headMapList.get(0).get(4));
            } else if (i == 9 || i == 10) {
                maintainItem.setTypeCode(9);
                maintainItem.setTypeName(headMapList.get(0).get(9));
            } else if (i == 11 || i == 12) {
                maintainItem.setTypeCode(11);
                maintainItem.setTypeName(headMapList.get(0).get(11));
            } else if (i == 13 || i == 14 || i == 15) {
                maintainItem.setTypeCode(13);
                maintainItem.setTypeName(headMapList.get(0).get(13));
            } else if (i == 18 || i == 19) {
                maintainItem.setTypeCode(18);
                maintainItem.setTypeName(headMapList.get(0).get(18));
            } else {
                maintainItem.setTypeCode(i);
                maintainItem.setTypeName(headMapList.get(0).get(i));
            }
            maintainItem.setFirstMileage(Integer.parseInt(splitValue[0]) * 10000);
            maintainItem.setRegularMileage(Integer.parseInt(splitValue[1]) * 10000);
            maintainItemList.add(maintainItem);
        }
        maintainItemRepository.saveAll(maintainItemList);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
//        saveData();
        System.out.println("done");
    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", headMap);
        headMapList.add(headMap);
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        resultList.addAll(list);
        log.info("存储数据库成功！");
    }
}
