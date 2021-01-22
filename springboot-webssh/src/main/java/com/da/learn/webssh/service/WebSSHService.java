package com.da.learn.webssh.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface WebSSHService {
    /**
     * @Description: 初始化ssh连接
     * @Param:
     * @return:
     */
    void initConnection(WebSocketSession session);

    /**
     * @Description: 处理客户段发的数据
     * @Param:
     * @return:
     */
    void recvHandle(String buffer, WebSocketSession session);

    /**
     * @Description: 数据写回前端 for websocket
     * @Param:
     * @return:
     */
    void sendMessage(WebSocketSession session, byte[] buffer) throws IOException;

    /**
     * @Description: 关闭连接
     * @Param:
     * @return:
     */
    void close(WebSocketSession session);
}
