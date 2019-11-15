package com.da.learn.netty.server.handler;

import com.da.learn.netty.protocol.request.ListGroupMembersRequestPacket;
import com.da.learn.netty.protocol.response.ListGroupMembersResponsePacket;
import com.da.learn.netty.session.Session;
import com.da.learn.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        // 1. 获取群的 ChannelGroup
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            responsePacket.setGroupId(groupId);
            responsePacket.setSessionList(Collections.emptyList());
        } else {
            // 2. 遍历群成员的 channel，对应的 session，构造群成员的信息
            List<Session> sessionList = new ArrayList<>();
            for (Channel channel : channelGroup) {
                Session session = SessionUtil.getSession(channel);
                sessionList.add(session);
            }
            // 3. 构建获取成员列表响应写回到客户端
            responsePacket.setGroupId(groupId);
            responsePacket.setSessionList(sessionList);
        }

        ctx.channel().writeAndFlush(responsePacket);
    }
}
