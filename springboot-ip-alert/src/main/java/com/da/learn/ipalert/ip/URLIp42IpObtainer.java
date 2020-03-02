package com.da.learn.ipalert.ip;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "ip.obtain", name = "obtainer", havingValue = "urlIp42IpObtainer")
public class URLIp42IpObtainer extends AbstractURLIpObtainer {

    private static final String URL = "http://ip.42.pl/raw";

    @Override
    protected String getUrl() {
        return URL;
    }

    @Override
    protected String getIpByResponseBody(String responseBody) {
        return responseBody;
    }
}
