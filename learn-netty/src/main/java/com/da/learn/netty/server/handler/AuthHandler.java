package com.da.learn.netty.server.handler;

import com.da.learn.netty.util.LoginUtil;
import com.da.learn.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

// 1. 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        if (!LoginUtil.hasLogin(ctx.channel())) {
//            ctx.channel().close();
//        } else {
//            ctx.pipeline().remove(this);
//            super.channelRead(ctx, msg);
//        }
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) {
//        if (LoginUtil.hasLogin(ctx.channel())) {
//            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
//        } else {
//            System.out.println("无登录验证，强制关闭连接!");
//        }
//    }

    // 2. 构造单例
    public static final AuthHandler INSTANCE = new AuthHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

}
