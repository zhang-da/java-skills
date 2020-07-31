package com.da.learn.cloud.serviceA.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("serviceB")
public interface ServiceBFeign {

    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public String getInfo();
}
