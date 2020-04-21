package com.da.learn.learnboot.maintainpush.maintain;

import com.da.learn.learnboot.maintainpush.maintain.core.*;
import org.junit.Test;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 按里程
 * 首保+定保
 */
public class MileageOnFirstAndRegularMaintainTest {


    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：6999.99
     * 上次里程：无
     * 结果：无保养项
     */
    @Test
    public void test_first_1() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        car.addItem(item);
        car.setMileage(6999.99D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        boolean result = vehicleMaintainResult == null || ObjectUtils.isEmpty(vehicleMaintainResult.getMaintainItemResults());
        assertTrue(result);
    }


    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：8000
     * 上次里程：无
     * 结果：有保养项  x-3000
     */
    @Test
    public void test_first_2() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        car.addItem(item);
        car.setMileage(8000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证首保
        assertEquals(0, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证首保时间段
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：首保-里程
        assertEquals(3, maintainItemResults.get(0).getMaintainResult().getType());
    }

    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：7000
     * 上次里程：无
     * 结果：有保养项  x-3000
     */
    @Test
    public void test_first_3() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        car.addItem(item);
        car.setMileage(7000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证首保
        assertEquals(0, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证首保时间段
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：首保-里程
        assertEquals(3, maintainItemResults.get(0).getMaintainResult().getType());
    }


    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：8000
     * 上次里程：7000
     * 结果：无保养项
     */
    @Test
    public void test_first_4() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        item.setLastMileage(7000D);
        car.addItem(item);
        car.setMileage(8000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        boolean result = vehicleMaintainResult == null || ObjectUtils.isEmpty(vehicleMaintainResult.getMaintainItemResults());
        assertTrue(result);
    }

    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：8000
     * 上次里程：6999.99
     * 结果：有保养项  x-3000
     */
    @Test
    public void test_first_5() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        item.setLastMileage(6999.99D);
        car.addItem(item);
        car.setMileage(8000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证首保
        assertEquals(0, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证首保时间段
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：首保-里程
        assertEquals(3, maintainItemResults.get(0).getMaintainResult().getType());
    }

    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：10000
     * 上次里程：8000
     * 结果：有保养项  x
     */
    @Test
    public void test_first_6() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        item.setLastMileage(8000D);
        car.addItem(item);
        car.setMileage(10000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证首保
        assertEquals(0, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证首保时间段
        assertEquals(2, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：首保-里程
        assertEquals(3, maintainItemResults.get(0).getMaintainResult().getType());
    }

    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：10000
     * 上次里程：无
     * 结果：有保养项  x
     */
    @Test
    public void test_first_7() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
//        item.setLastMileage(8000D);
        car.addItem(item);
        car.setMileage(10000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证首保
        assertEquals(0, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证首保时间段
        assertEquals(2, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：首保-里程
        assertEquals(3, maintainItemResults.get(0).getMaintainResult().getType());
    }

    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：15000
     * 上次里程：无
     * 结果：有保养项  x
     */
    @Test
    public void test_first_8() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
//        item.setLastMileage(8000D);
        car.addItem(item);
        car.setMileage(15000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证首保
        assertEquals(0, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证首保时间段
        assertEquals(2, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：首保-里程
        assertEquals(3, maintainItemResults.get(0).getMaintainResult().getType());
    }

    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：15000
     * 上次里程：10000
     * 结果：无保养项
     */
    @Test
    public void test_first_9() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        item.setLastMileage(10000D);
        car.addItem(item);
        car.setMileage(15000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        boolean result = vehicleMaintainResult == null || ObjectUtils.isEmpty(vehicleMaintainResult.getMaintainItemResults());
        assertTrue(result);
    }

    /**
     * 首保：9000
     * 定保：9000
     * 当前里程：7999.99
     * 上次里程：无
     * 结果：无保养项
     */
    @Test
    public void test_first_10() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(9000D);
        item.setRegularMileage(9000D);
//        item.setLastMileage(10000D);
        car.addItem(item);
        car.setMileage(7999.99D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        boolean result = vehicleMaintainResult == null || ObjectUtils.isEmpty(vehicleMaintainResult.getMaintainItemResults());
        assertTrue(result);
    }

    /**
     * 首保：9000
     * 定保：9000
     * 当前里程：8000
     * 上次里程：无
     * 结果：有保养项 x-1000
     */
    @Test
    public void test_first_11() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(9000D);
        item.setRegularMileage(9000D);
//        item.setLastMileage(10000D);
        car.addItem(item);
        car.setMileage(8000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证首保
        assertEquals(0, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证首保时间段
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：首保-里程
        assertEquals(3, maintainItemResults.get(0).getMaintainResult().getType());
    }

    /**
     * 首保：9000
     * 定保：9000
     * 当前里程：8000
     * 上次里程：7000
     * 结果：有保养项 x-1000
     */
    @Test
    public void test_first_12() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(9000D);
        item.setRegularMileage(9000D);
        item.setLastMileage(7000D);
        car.addItem(item);
        car.setMileage(8000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证首保
        assertEquals(0, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证首保时间段
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：首保-里程
        assertEquals(3, maintainItemResults.get(0).getMaintainResult().getType());
    }


    /**
     * 首保：9000
     * 定保：9000
     * 当前里程：8000
     * 上次里程：8000
     * 结果：无
     */
    @Test
    public void test_first_13() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(9000D);
        item.setRegularMileage(9000D);
        item.setLastMileage(8000D);
        car.addItem(item);
        car.setMileage(8000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        boolean result = vehicleMaintainResult == null || ObjectUtils.isEmpty(vehicleMaintainResult.getMaintainItemResults());
        assertTrue(result);
    }

    /**
     * 首保：9000
     * 定保：9000
     * 当前里程：9000
     * 上次里程：8000
     * 结果：有保养项 x
     */
    @Test
    public void test_first_14() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(9000D);
        item.setRegularMileage(9000D);
        item.setLastMileage(8000D);
        car.addItem(item);
        car.setMileage(9000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证首保
        assertEquals(0, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证首保时间段
        assertEquals(2, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：首保-里程
        assertEquals(3, maintainItemResults.get(0).getMaintainResult().getType());
    }


    /**
     * 首保：9000
     * 定保：9000
     * 当前里程：10000
     * 上次里程：无
     * 结果：有保养项 x
     */
    @Test
    public void test_first_15() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(9000D);
        item.setRegularMileage(9000D);
//        item.setLastMileage(8000D);
        car.addItem(item);
        car.setMileage(10000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证首保
        assertEquals(0, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证首保时间段
        assertEquals(2, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：首保-里程
        assertEquals(3, maintainItemResults.get(0).getMaintainResult().getType());
    }

    /**
     * 首保：9000
     * 定保：9000
     * 当前里程：10000
     * 上次里程：9000
     * 结果：无保养
     */
    @Test
    public void test_first_16() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(9000D);
        item.setRegularMileage(9000D);
        item.setLastMileage(9000D);
        car.addItem(item);
        car.setMileage(10000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        boolean result = vehicleMaintainResult == null || ObjectUtils.isEmpty(vehicleMaintainResult.getMaintainItemResults());
        assertTrue(result);
    }


    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：17000
     * 上次里程：无
     * 结果：有保养 定保 x-3000
     */
    @Test
    public void test_regular_1() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
//        item.setLastMileage(9000D);
        car.addItem(item);
        car.setMileage(17000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证定保
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证定保时间段
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：定保-里程
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getType());
    }


    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：17000
     * 上次里程：9000D
     * 结果：有保养 定保 x-3000
     */
    @Test
    public void test_regular_2() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        item.setLastMileage(9000D);
        car.addItem(item);
        car.setMileage(17000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证定保
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证定保时间段
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：定保-里程
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getType());
    }


    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：17000
     * 上次里程：15000
     * 结果：有保养 定保 x-3000
     */
    @Test
    public void test_regular_3() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        item.setLastMileage(15000D);
        car.addItem(item);
        car.setMileage(17000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证定保
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证定保时间段
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：定保-里程
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getType());
    }

    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：17001
     * 上次里程：17000
     * 结果：无保养
     */
    @Test
    public void test_regular_4() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        item.setLastMileage(17000D);
        car.addItem(item);
        car.setMileage(17001D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        boolean result = vehicleMaintainResult == null || ObjectUtils.isEmpty(vehicleMaintainResult.getMaintainItemResults());
        assertTrue(result);
    }

    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：20000
     * 上次里程：17000
     * 结果：有定保  x
     */
    @Test
    public void test_regular_5() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        item.setLastMileage(17000D);
        car.addItem(item);
        car.setMileage(20000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证定保
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证定保时间段
        assertEquals(2, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：定保-里程
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getType());
    }


    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：20000
     * 上次里程：无
     * 结果：有定保  x
     */
    @Test
    public void test_regular_6() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
//        item.setLastMileage(17000D);
        car.addItem(item);
        car.setMileage(20000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证定保
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证定保时间段
        assertEquals(2, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：定保-里程
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getType());
    }


    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：21000
     * 上次里程：19000
     * 结果：有定保  x
     */
    @Test
    public void test_regular_7() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        item.setLastMileage(19000D);
        car.addItem(item);
        car.setMileage(21000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证定保
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证定保时间段
        assertEquals(2, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：定保-里程
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getType());
    }

    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：21000
     * 上次里程：20005
     * 结果：无
     */
    @Test
    public void test_regular_8() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
        item.setLastMileage(20005D);
        car.addItem(item);
        car.setMileage(21000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        boolean result = vehicleMaintainResult == null || ObjectUtils.isEmpty(vehicleMaintainResult.getMaintainItemResults());
        assertTrue(result);
    }


    /**
     * 首保：10000
     * 定保：10000
     * 当前里程：40000
     * 上次里程：无
     * 结果：有定保 x
     */
    @Test
    public void test_regular_9() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(10000D);
        item.setRegularMileage(10000D);
//        item.setLastMileage(20005D);
        car.addItem(item);
        car.setMileage(40000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证定保
        assertEquals(3, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证定保时间段
        assertEquals(2, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：定保-里程
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getType());
    }


    /**
     * 首保：3000
     * 定保：3000
     * 当前里程：3000
     * 上次里程：无
     * 结果：有定保 x-3000
     */
    @Test
    public void test_regular_10() {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy
                .buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule()
                        , new DefaultRegularByMileageMaintainRule());

        CarForMaintainJudge car = new CarForMaintainJudge();
        MaintainItemImpl item = new MaintainItemImpl();
        item.setFirstMileage(3000D);
        item.setRegularMileage(3000D);
//        item.setLastMileage(20005D);
        car.addItem(item);
        car.setMileage(3000D);


        VehicleMaintainResult vehicleMaintainResult = maintainStrategy.judgeMaintain(car);
        List<MaintainItemResult> maintainItemResults = vehicleMaintainResult.getMaintainItemResults();
        //满足一条保养规则
        assertEquals(1, maintainItemResults.size());
        //有保养
        assertTrue(maintainItemResults.get(0).getMaintainResult().hasMaintain());
        // 验证定保
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getStage());
        // 验证定保时间段
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getSection());
        //验证类型：定保-里程
        assertEquals(1, maintainItemResults.get(0).getMaintainResult().getType());
    }

}