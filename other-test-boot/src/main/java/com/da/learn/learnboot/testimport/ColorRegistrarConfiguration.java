package com.da.learn.learnboot.testimport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColorRegistrarConfiguration {

    @Bean
    public Yellow yellow() {
        return new Yellow();
    }
}
