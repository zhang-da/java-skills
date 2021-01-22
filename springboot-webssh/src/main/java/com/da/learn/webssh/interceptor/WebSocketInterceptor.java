package com.da.learn.webssh.interceptor;

import com.da.learn.webssh.constant.ConstantPool;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.UUID;

public class WebSocketInterceptor implements HandshakeInterceptor {
    /**
     * Handler处理前调用
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            //生成一个UUID，这里由于是独立的项目，没有用户模块，所以可以用随机的UUID
            //但是如果要集成到自己的项目中，需要将其改为自己识别用户的标识
            String uuid = UUID.randomUUID().toString().replace("-","");
            //将uuid放到websocketsession中
            attributes.put(ConstantPool.USER_UUID_KEY, uuid);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
