package com.da.learn.netty.server.handler;

import com.da.learn.netty.protocol.request.LoginRequestPacket;
import com.da.learn.netty.protocol.response.LoginResponsePacket;
import com.da.learn.netty.session.Session;
import com.da.learn.netty.util.LoginUtil;
import com.da.learn.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println(new Date() + ": 收到客户端登录请求……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        loginResponsePacket.setUserName(msg.getUserName());
        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + msg.getUserName() + "]登录成功");
            SessionUtil.bindSession(new Session(userId, msg.getUserName()), ctx.channel());
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        // 登录响应
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
