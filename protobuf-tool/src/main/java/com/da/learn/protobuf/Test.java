package com.da.learn.protobuf;

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

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        int step = 3;

        for (int i = 0; i<list.size(); i += step){
            int start = i;
            int end = i + step > list.size() ? list.size() : i + step;
            List<Integer> list1 = list.subList(start, end);
            System.out.println("======");
            System.out.println(list1);
        }


    }
}
