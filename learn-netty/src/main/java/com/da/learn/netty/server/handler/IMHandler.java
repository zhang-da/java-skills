package com.da.learn.netty.server.handler;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler INSTANCE = new IMHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        //为了更快，可以放在一个map里获取INSTANCE
        CommandEnum command = CommandEnum.getCommandEnumByCommand(msg.getCommand());
        if (command == null) {
            return;
        }
        SimpleChannelInboundHandler<? extends Packet> handler = command.getHandler();
        if (handler == null) {
            return;
        }
        handler.channelRead(ctx, msg);

    }
}
