package com.da.learn.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * handlerAdded() -> channelRegistered() -> channelActive() -> channelRead() -> channelReadComplete()
 *
 * channelInactive() -> channelUnregistered() -> handlerRemoved()
 */
public class LifeCyCleTestHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /**
         * 指的是当检测到新连接之后，调用 ch.pipeline().addLast(new LifeCyCleTestHandler()); 之后的回调，
         * 表示在当前的 channel 中，已经成功添加了一个 handler 处理器。
         */

        /**
         * handlerAdded() 与 handlerRemoved()
         * 这两个方法通常可以用在一些资源的申请和释放
         */
        System.out.println("逻辑处理器被添加：handlerAdded()");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 绑定到线程(NioEventLoop)：channelRegistered()");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 1 对我们的应用程序来说，这两个方法表明的含义是 TCP 连接的建立与释放，
         *   通常我们在这两个回调里面统计单机的连接数，channelActive() 被调用，连接数加一，channelInActive() 被调用，连接数减一
         * 2 另外，我们也可以在 channelActive() 方法中，实现对客户端连接 ip 黑白名单的过滤，
         */
        System.out.println("channel 准备就绪：channelActive()");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel 有数据可读：channelRead()");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /**
         * 我们在每次向客户端写数据的时候，都通过 writeAndFlush() 的方法写并刷新到底层，
         * 其实这种方式不是特别高效，我们可以在之前调用 writeAndFlush() 的地方都调用 write() 方法，
         * 然后在这个方面里面调用 ctx.channel().flush() 方法，相当于一个批量刷新的机制
         */
        System.out.println("channel 某次数据读完：channelReadComplete()");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 被关闭：channelInactive()");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 取消线程(NioEventLoop) 的绑定: channelUnregistered()");
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("逻辑处理器被移除：handlerRemoved()");
        super.handlerRemoved(ctx);
    }
}
