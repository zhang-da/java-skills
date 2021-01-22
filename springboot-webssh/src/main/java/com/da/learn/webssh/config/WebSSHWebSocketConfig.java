package com.da.learn.webssh.config;

import com.da.learn.webssh.handler.WebSSHWebSocketHandler;
import com.da.learn.webssh.interceptor.WebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSSHWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    WebSSHWebSocketHandler webSSHWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //socket通道
        //指定处理器和路径，并设置跨域
        webSocketHandlerRegistry.addHandler(webSSHWebSocketHandler, "/webssh")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");
    }
}
