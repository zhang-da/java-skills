package com.da.learn.netty.server.handler;

import com.da.learn.netty.protocol.request.GroupMessageRequestPacket;
import com.da.learn.netty.protocol.response.GroupMessageResponsePacket;
import com.da.learn.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

// 1. 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        String groupId = msg.getGroupId();

        // 2. 拿到群聊对应的 channelGroup，写到每个客户端
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            responsePacket.setSuccess(false);
            responsePacket.setReason("群组id不存在");
            ctx.channel().writeAndFlush(responsePacket);
        } else {
            // 1.拿到 groupId 构造群聊消息的响应
            responsePacket.setSuccess(true);
            responsePacket.setGroupId(groupId);
            responsePacket.setMessage(msg.getMessage());
            responsePacket.setFromUser(SessionUtil.getSession(ctx.channel()));

            channelGroup.writeAndFlush(responsePacket);
        }


    }
}
