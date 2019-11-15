package com.da.learn.netty.server.handler;

import com.da.learn.netty.protocol.request.JoinGroupRequestPacket;
import com.da.learn.netty.protocol.response.JoinGroupResponsePacket;
import com.da.learn.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 添加进去
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            responsePacket.setSuccess(false);
            responsePacket.setMessage("群组id不存在");
        } else {
            channelGroup.add(ctx.channel());
            // 2. 构造加群响应发送给客户端
            responsePacket.setSuccess(true);
            responsePacket.setGroupId(groupId);
        }
        ctx.channel().writeAndFlush(responsePacket);
    }
}
