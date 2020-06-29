package com.da.learn.extension;

import com.da.learn.extension.ApplicationContextInitializer.TestApplicationContextInitializer1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExtensionApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ExtensionApplication.class);
        springApplication.addInitializers(new TestApplicationContextInitializer1());
        springApplication.run();
    }
}
