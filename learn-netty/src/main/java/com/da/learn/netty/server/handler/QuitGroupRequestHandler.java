package com.da.learn.netty.server.handler;

import com.da.learn.netty.protocol.request.QuitGroupRequestPacket;
import com.da.learn.netty.protocol.response.QuitGroupResponsePacket;
import com.da.learn.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 移除
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            responsePacket.setSuccess(false);
            responsePacket.setMessage("群组id不存在");
        } else {
            channelGroup.remove(ctx.channel());
            // 2. 构造退群响应发送给客户端
            responsePacket.setGroupId(msg.getGroupId());
            responsePacket.setSuccess(true);
        }
        ctx.channel().writeAndFlush(responsePacket);

    }
}
