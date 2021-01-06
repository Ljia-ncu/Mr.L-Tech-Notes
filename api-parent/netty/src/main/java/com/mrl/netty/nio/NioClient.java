package com.mrl.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: NioClient
 * @Description
 * @Author Mr.L
 * @Date 2021/1/6 16:56
 * @Version 1.0
 */
public class NioClient {

    public static void main(String[] args) throws InterruptedException {
        String host = "127.0.0.1";
        final int port = 8080;
        new NioClient().start(host, port);

        Thread.currentThread().join();
    }

    private void start(String host, int port) {
        new Thread(new ClientHeart(host, port), "nio-client").start();
    }

    private class ClientHeart implements Runnable {
        private final String host;
        private final int port;     //注意是server ip:port

        private Selector selector;
        private SocketChannel socketChannel;

        private volatile boolean stop;

        public ClientHeart(String host, int port) {
            this.host = host;
            this.port = port;
            try {
                //开启多路复用器以及socketChannel
                selector = Selector.open();
                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                //进行连接
                doConnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!stop) {
                try {
                    selector.select(1000);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectionKeys.iterator();
                    SelectionKey key;
                    while (it.hasNext()) {
                        key = it.next();
                        try {
                            handleInput(key);
                        } catch (Exception e) {
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) {
                                    key.channel().close();
                                }
                            }
                        } finally {
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

        private void handleInput(SelectionKey key) throws IOException {
            if (key.isValid()) {
                SocketChannel sc = (SocketChannel) key.channel();
                if (key.isConnectable()) {
                    if (sc.finishConnect()) {
                        //连接完成，发送消息到服务端，这里再注册一个读监听，等待服务端响应
                        sc.register(selector, SelectionKey.OP_READ);
                        doWrite();
                    } else {
                        System.exit(1);
                    }
                }
                if (key.isReadable()) {
                    //如果可读，这里将接受到服务端的消息
                    ByteBuffer readBuff = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(readBuff);
                    if (readBytes > 0) {
                        readBuff.flip();
                        byte[] bytes = new byte[readBuff.remaining()];
                        readBuff.get(bytes);
                        String response = "client received! -> " + new String(bytes, StandardCharsets.UTF_8);
                        System.out.println(response);
                    } else if (readBytes < 0) {
                        key.cancel();
                        sc.close();
                    }
                }
            }
        }

        private void doConnect() throws IOException {
            //如果连接上了，就将socketChannel注册到多路复用器上，并监听可读事件 用来获取服务端的应答
            if (socketChannel.connect(new InetSocketAddress(host, port))) {
                socketChannel.register(selector, SelectionKey.OP_READ);
            } else {
                //这里并不代表没有没有连接成功，只是服务端Tcp连接的握手应答包还没收到，所以这里监听连接事件 用来获取服务端的连接
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        }

        private void doWrite() throws IOException {
            ByteBuffer writeBuff = ByteBuffer.allocate(1024);
            writeBuff.put("client:are u ok".getBytes());
            writeBuff.flip();
            socketChannel.write(writeBuff);
        }
    }
}
