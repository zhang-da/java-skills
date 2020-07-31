package com.da.learn.cloud.prometheus.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class StatisticMvcInterceptor extends HandlerInterceptorAdapter {
    private static final Counter COUNTER = Counter.builder("http请求统计")
            .tag("httpCount", "httpCount")
            .description("http请求统计")
            .register(Metrics.globalRegistry);

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        COUNTER.increment();
    }
}
