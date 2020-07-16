package com.da.learn.protobuf;

import protocol.BehaviorReportDataInfo;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
//        List<String> flightKeyList = flightKeySet.stream().limit(limitNum).collect(Collectors.toList());
//        // 旗舰店限价功能, 代理商不得比旗舰店价格低
//        List<Integer> limitPriceAgents = agentStatusService.getLimitPriceFlagshipAgents();
//        int firstScreenNum = tripType == TripType.OW.id() ? 5 : 3;
//        int firstScreenEnd = flightKeyList.size() >= firstScreenNum ? firstScreenNum : flightKeyList.size();
//        futures.add(readPool.submit(() -> getMinPriceVoByGroup(ctx, limitPriceAgents, flightKeyList.subList(0, firstScreenEnd), singlePriceVoTable)));
//        int step = (tripType == TripType.OW.id()) ? 15 : 30;
//        for (int i = firstScreenEnd; i < flightKeyList.size(); i += step) {
//            int start = i;
//            int end = i + step > flightKeyList.size() ? flightKeyList.size() : i + step;
//            Future<List<SinglePriceVo>> f = readPool.submit(() -> getMinPriceVoByGroup(ctx, limitPriceAgents, flightKeyList.subList(start, end), singlePriceVoTable));
//            futures.add(f);
//        }

        BehaviorReportDataInfo.BehaviorReportData.Builder data = BehaviorReportDataInfo.BehaviorReportData.newBuilder();
        data.setTimeStamp(System.currentTimeMillis());
        data.setDeviceId("deviceId");
        data.setDeviceModel("apple");
        data.setDeviceType("phone");
        data.setAppVersion("appVersion");
        data.setAppName("siji");
        data.setSystemType("ios");
        data.setReportId("123");
        data.setDataType("1");
        data.setProtocolVersion("1.1.1");
        data.setNetwork("4G");
        data.setIp("127.0.0.1");
        data.setLongitude("123.123");
        data.setLatitude("39.899");
        data.setCity("city");
        data.setUserId("123");
        data.setPhone("12301230123");
        data.setStartTime(System.currentTimeMillis());
        data.setEndTime(System.currentTimeMillis());
        data.setParams("others");
        BehaviorReportDataInfo.BehaviorReportData build = data.build();
        System.out.println(build.toString());


    }
}
