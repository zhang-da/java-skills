package com.da.learn.ipalert.handler;

import com.da.learn.ipalert.alerter.Alert;
import com.da.learn.ipalert.alerter.Alerter;
import com.da.learn.ipalert.cache.Cache;
import com.da.learn.ipalert.ip.IpObtainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Slf4j
@Component("urlIpAlertHandler")
public class URLIpAlertHandler implements IpAlertHandler {

    @Resource
    private IpObtainer ipObtainer;

    @Resource(name = "emailAlert")
    private Alert emailAlert;


    @Value("${ip.obtain.error.max_time}")
    private Integer maxIpObtainErrorTime;

    @Override
    public void handle() {
        String oldIp = ipObtainer.obtainOld();
        String newIp = ipObtainer.obtainNew();
        Alerter alerter = new Alerter(emailAlert);
        if (StringUtils.isEmpty(newIp) || newIp.startsWith(IpObtainer.ERROR_IP)) {
            String errorTime = Cache.get(Cache.CacheType.WRONG_IP_TIMES);
            if (StringUtils.isEmpty(errorTime)) {
                return;
            }
//            alerter.alert("获取ip出错，次数：".concat(errorTime).concat("。\n").concat(newIp));
            log.info("获取ip出错，次数：".concat(errorTime).concat("。\n").concat(newIp));
            if (Integer.parseInt(errorTime) >= maxIpObtainErrorTime) {
                Cache.del(Cache.CacheType.WRONG_IP_TIMES);
            }
            return;
        }
        if (!StringUtils.isEmpty(oldIp) && oldIp.equals(newIp)) {
            //不是第一次，没有变化
            return;
        }

        alerter.alert(newIp);
        //存储
        ipObtainer.saveIp(newIp);
    }
}
