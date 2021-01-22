package com.da.learn.webssh.handler;

import com.da.learn.webssh.constant.ConstantPool;
import com.da.learn.webssh.service.WebSSHService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
public class WebSSHWebSocketHandler implements WebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(WebSSHWebSocketHandler.class);

    @Autowired
    private WebSSHService webSSHService;

    /**
     * 用户连接上WebSocket的回调
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("用户:{},连接WebSSH", session.getAttributes().get(ConstantPool.USER_UUID_KEY));
        //调用初始化连接
        webSSHService.initConnection(session);
    }

    /**
     * 收到消息的回调
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            logger.info("用户:{},发送命令:{}", session.getAttributes().get(ConstantPool.USER_UUID_KEY), message.toString());
            //调用service接收消息
            webSSHService.recvHandle(((TextMessage) message).getPayload(), session);
        } else if (message instanceof BinaryMessage) {

        } else if (message instanceof PongMessage) {

        } else {
            System.out.println("Unexpected WebSocket message type: " + message);
        }
    }

    /**
     * 出现错误的回调
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("数据传输错误");
    }

    /**
     * 连接关闭的回调
     *
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.info("用户:{}断开webssh连接", String.valueOf(session.getAttributes().get(ConstantPool.USER_UUID_KEY)));
        //调用service关闭连接
        webSSHService.close(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
