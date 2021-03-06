package com.da.learn.ipalert.ip;

import com.da.learn.ipalert.cache.Cache;
import com.da.learn.ipalert.tools.StringCheckTool;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
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
            return ERROR_IP.concat(":").concat(ip);
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
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .callTimeout(5000L, TimeUnit.MILLISECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            String bodyString = response.body().string();
            log.info("REQUEST：{}，RESULT：{}", url, bodyString);
            return bodyString;
        } catch (Exception e) {
            log.error("error: {}", e);
            return e.toString();
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
