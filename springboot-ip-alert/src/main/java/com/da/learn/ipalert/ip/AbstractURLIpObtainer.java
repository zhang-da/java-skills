package com.da.learn.ipalert.ip;

import com.da.learn.ipalert.cache.Cache;
import com.da.learn.ipalert.tools.StringCheckTool;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractURLIpObtainer implements IpObtainer {

    @Override
    public String obtainOld() {
        return Cache.get(Cache.CacheType.IP);
    }

    @Override
    public String obtainNew() {
        String responseBody = getResponseByUrl(getUrl());
        String ip = getIpByResponseBody(responseBody);
        if (!StringCheckTool.checkIp(ip)) {
            handleError();
            return ERROR_IP;
        }
        return ip;
    }

    @Override
    public void saveIp(String ip) {
        Cache.set(Cache.CacheType.IP, ip);
    }

    protected abstract String getUrl();

    protected abstract String getIpByResponseBody(String responseBody);

    private String getResponseByUrl(String url) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            return null;
        }
    }

    private void handleError() {
        String errTimes = Cache.get(Cache.CacheType.WRONG_IP_TIMES);
        if (StringUtils.isEmpty(errTimes)) {
            errTimes = "0";
        }
        errTimes = String.valueOf(Integer.parseInt(errTimes) + 1);
        Cache.set(Cache.CacheType.WRONG_IP_TIMES, errTimes);
    }

}
