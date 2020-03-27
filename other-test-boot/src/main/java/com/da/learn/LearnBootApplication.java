package com.da.learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class LearnBootApplication {

    public static void main(String[] args) throws Exception {

//        SpringApplication.run(LearnBootApplication.class, args);
        SpringApplication springApplication = new SpringApplication(LearnBootApplication.class);
        springApplication.addInitializers((ConfigurableApplicationContext applicationContext) -> {
            System.out.println("test:ApplicationContextInitializerDemo#initialize run...");
        });
        springApplication.setBannerMode(Banner.Mode.LOG);
        springApplication.run();
    }


}
