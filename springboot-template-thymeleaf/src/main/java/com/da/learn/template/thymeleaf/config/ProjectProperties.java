package com.da.learn.template.thymeleaf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {
    // 是否开启验证码
    private boolean captchaOpen = false;
}
