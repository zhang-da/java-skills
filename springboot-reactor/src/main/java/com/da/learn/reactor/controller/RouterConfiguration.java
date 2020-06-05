package com.da.learn.reactor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfiguration {

    @Autowired
    private DemoHandler demoHandler;

    @Bean
    public RouterFunction<ServerResponse> demoRouter() {
        return RouterFunctions.route(GET("/test1").and(accept(MediaType.TEXT_PLAIN)), demoHandler::test)
                .andRoute(GET("/list1").and(accept(MediaType.APPLICATION_JSON)), demoHandler::list);
    }
}
