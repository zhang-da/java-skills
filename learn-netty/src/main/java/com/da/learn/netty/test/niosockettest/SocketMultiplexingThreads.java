package com.da.learn.netty.test.niosockettest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * nio 多路复用 java
 * 多线程
 */
public class SocketMultiplexingThreads {

    private ServerSocketChannel server = null;

    private Selector selector1 = null;
    private Selector selector2 = null;
    private Selector selector3 = null;
    int port = 9090;

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            selector1 = Selector.open();
            selector2 = Selector.open();
            selector3 = Selector.open();
            server.register(selector1, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketMultiplexingThreads server = new SocketMultiplexingThreads();
        server.initServer();

        NioThread t1 = new NioThread(server.selector1, 2);
        NioThread t2 = new NioThread(server.selector2);
        NioThread t3 = new NioThread(server.selector3);

        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.start();
        t3.start();

        System.out.println("服务器启动了。。。");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class NioThread extends Thread {

        Selector selector = null;
        static int selectors = 0;
        int id = 0;

        boolean boss = false;

        static BlockingQueue<SocketChannel>[] queues;

        static AtomicInteger idx = new AtomicInteger();

        public NioThread() {
        }

        public NioThread(Selector selector, int n) {    //boss
            this.selector = selector;
            this.selectors = n;

            boss = true;

            queues = new LinkedBlockingDeque[selectors];
            for (int i = 0; i < n; i++) {
                queues[i] = new LinkedBlockingDeque<>();
            }
            System.out.println("Boss 启动");
        }

        public NioThread(Selector selector) {    //worker
            this.selector = selector;

            id = idx.getAndIncrement() % selectors;

            System.out.println("Worker : " + id + " 启动");
        }

        @Override
        public void run() {
            try {
                while (true) {
                    while (selector.select(10) > 0) {
                        Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            iterator.remove();
                            if (key.isAcceptable()) {
                                acceptHandler(key);
                            } else if (key.isReadable()) {
                                readHandler(key);
                            }
                        }
                    }

                    if (!boss && !queues[id].isEmpty()) {
                        ByteBuffer buffer = ByteBuffer.allocate(8192);
                        SocketChannel client = queues[id].take();
                        client.register(selector, SelectionKey.OP_READ, buffer);
                        System.out.println("新客户端： " + client.socket().getPort() + "分配给worker:" +  id);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void readHandler(SelectionKey key) {
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            int read = 0;
            try {
                while (true) {
                    read = client.read(buffer);
                    if (read > 0) {
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            client.write(buffer);
                        }
                        buffer.clear();
                    } else if (read == 0) {
                        break;
                    } else {   // -1 close_wait
                        client.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void acceptHandler(SelectionKey key) {
            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel client = ssc.accept();
                client.configureBlocking(false);

                int num = idx.getAndIncrement() % selectors;

                queues[num].add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
