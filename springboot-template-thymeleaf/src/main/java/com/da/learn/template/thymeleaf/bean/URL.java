package com.da.learn.template.thymeleaf.bean;

import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Data
public class URL {

    private String url;

    public URL() {

    }

    /**
     * 封装URL地址，自动添加应用上下文路径
     *
     * @param url URL地址
     */
    public URL(String url) {
        this.url = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getContextPath() + url;
    }

    @Override
    public String toString() {
        return this.url;
    }
}
