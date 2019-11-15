package com.da.learn.netty.client.handler;

import com.da.learn.netty.protocol.response.GroupMessageResponsePacket;
import com.da.learn.netty.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) throws Exception {
        if (msg.getSuccess()) {
            String fromGroupId = msg.getGroupId();
            Session fromUser = msg.getFromUser();
            System.out.println("收到群[" + fromGroupId + "]中[" + fromUser + "]发来的消息：" + msg.getMessage());
        } else {
            System.out.println("发送消息失败，原因：" + msg.getReason());
        }
    }
}
