package com.da.learn.netty.client.handler;

import com.da.learn.netty.protocol.response.LogoutResponsePacket;
import com.da.learn.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        if (msg.getSuccess()) {
            SessionUtil.unBindSession(ctx.channel());
            System.out.println("登出成功");
        }
    }
}
