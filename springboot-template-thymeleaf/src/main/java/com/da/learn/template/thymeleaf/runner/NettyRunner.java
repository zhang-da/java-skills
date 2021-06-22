package com.da.learn.template.thymeleaf.runner;

import com.da.learn.template.thymeleaf.service.SearchHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class NettyRunner implements ApplicationRunner {

    private static final int PORT = 8000;

    @Autowired
    private SearchHandler searchHandler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
//                        ch.pipeline().addLast(new LifeCyCleTestHandler());
                        //拆包
//                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        //空闲检测
//                        ch.pipeline().addLast(new IMIdleStateHandler());
//                        ch.pipeline().addLast(new Spliter());
//                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
//                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(AuthHandler.INSTANCE);
//                        ch.pipeline().addLast(IMHandler.INSTANCE);

                        ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                        ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                        ch.pipeline().addLast(searchHandler);
                    }
                });


        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                log.error("端口[" + port + "]绑定失败!");
            }
        });
    }
}
