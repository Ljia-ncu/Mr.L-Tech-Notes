package com.mrl.netty.nio;

import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: NioServer
 * @Description
 * @Author Mr.L
 * @Date 2021/1/6 15:27
 * @Version 1.0
 */
public class NioServer {

    public static void main(String[] args) throws InterruptedException {
        final int port = 8080;
        new NioServer().start(port);

        //阻塞main线程退出
        Thread.currentThread().join();
    }

    private void start(int port) {
        new Thread(new ServerHeart(port), "nio-server").start();
    }

    private class ServerHeart implements Runnable {

        private Selector selector;
        private volatile boolean stop;

        public ServerHeart(int port) {
            try {
                //开启serverSocketChannel，设置为非阻塞，绑定当前ip的port
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);

                //开启多路复用器，并注册serverSocketChannel 监听OP_ACCEPT事件 用来accept客户端的连接
                selector = Selector.open();
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    //每隔1s
                    selector.select(1000);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectionKeys.iterator();
                    SelectionKey key;
                    while (it.hasNext()) {
                        key = it.next();
                        try {
                            handlerInput(key);
                        } catch (Exception e) {
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) {
                                    key.channel().close();
                                }
                            }
                        } finally {
                            //删除
                            it.remove();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handlerInput(SelectionKey key) throws IOException {
            if (key.isValid()) {
                //如果isAcceptable，ServerSocketChannel就进行accept，同时配置这条通道非阻塞，并监听它的可读事件
                if (key.isAcceptable()) {
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                }
                //如果isReadable，直接用ByteBuffer进行接收/响应
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    //开辟1MB
                    ByteBuffer readBuff = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(readBuff);
                    if (readBytes > 0) {
                        readBuff.flip();
                        byte[] bytes = new byte[readBuff.remaining()];
                        readBuff.get(bytes);
                        String response = "server received! -> " + new String(bytes, StandardCharsets.UTF_8);
                        System.out.println(response);
                        doWrite(sc, "server: i'm ok.");
                    } else if (readBytes < 0) {
                        key.cancel();
                        sc.close();
                    }
                }
            }
        }

        private void doWrite(SocketChannel sc, String response) throws IOException {
            if (!StrUtil.isEmpty(response)) {
                byte[] bytes = response.getBytes();
                ByteBuffer writeBuff = ByteBuffer.allocate(bytes.length);
                writeBuff.put(bytes);
                writeBuff.flip();
                sc.write(writeBuff);
            }
        }
    }
}