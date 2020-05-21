package com.da.learn.netty.test.niosockettest;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * NIO 非阻塞
 * 可通过  nc ip port 测试
 * 问题： 资源浪费
 */
public class SimpleNIOServer {

    public static void main(String[] args) throws Exception {

        List<SocketChannel> clientList = new ArrayList<>();

        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(9000));
        ss.configureBlocking(false);   //非阻塞  noio

        while (true) {
            Thread.sleep(1000);
            SocketChannel client = ss.accept();
            if (client == null) {
                System.out.println("null....");
            } else {
                client.configureBlocking(false);
                int port = client.socket().getPort();
                System.out.println("client----port: " + port);
                clientList.add(client);
            }

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);

            for (SocketChannel c : clientList) {      //串行化  公用一个byteBuffer
                int num = c.read(byteBuffer);
                if (num > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.limit()];
                    byteBuffer.get(bytes);
                    String s = new String(bytes);
                    System.out.println(c.socket().getPort() + "收到：" + s);
                    byteBuffer.clear();
                }
            }
        }
    }
}
