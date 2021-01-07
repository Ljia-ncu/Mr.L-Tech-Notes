package com.mrl.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: AioClient
 * @Description
 * @Author Mr.L
 * @Date 2021/1/7 15:01
 * @Version 1.0
 */
public class AioClient {

    public static void main(String[] args) throws InterruptedException {
        new AioClient().start("127.0.0.1", 8080);
        Thread.currentThread().join();
    }

    private void start(String host, int port) {
        new Thread(new ClientHeart(host, port)).start();
    }

    private class ClientHeart implements Runnable {
        private String host;
        private int port;

        private AsynchronousSocketChannel socketChannel;
        private CountDownLatch latch;

        public ClientHeart(String host, int port) {
            this.host = host;
            this.port = port;
            try {
                socketChannel = AsynchronousSocketChannel.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //每个CompletionHandler最好单独用个类实现，这里只作为demo
        @Override
        public void run() {
            latch = new CountDownLatch(1);
            //连接到服务端，设置一个回调CompletionHandler
            socketChannel.connect(new InetSocketAddress(host, port), this, new CompletionHandler<Void, ClientHeart>() {
                @Override
                public void completed(Void result, ClientHeart attachment) {
                    ByteBuffer writeBuff = ByteBuffer.allocate(1024);
                    writeBuff.put("client:are u ok".getBytes());
                    writeBuff.flip();
                    //客户端连接成功后，就发送一条消息
                    socketChannel.write(writeBuff, writeBuff, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer byteBuffer) {
                            if (byteBuffer.hasRemaining()) {
                                socketChannel.write(byteBuffer, byteBuffer, this);
                            } else {
                                ByteBuffer readBuff = ByteBuffer.allocate(1024);
                                //发送成功之后，就再读取来自服务端的应答
                                socketChannel.read(readBuff, readBuff, new CompletionHandler<Integer, ByteBuffer>() {
                                    @Override
                                    public void completed(Integer result, ByteBuffer attachment) {
                                        readBuff.flip();
                                        byte[] bytes = new byte[readBuff.remaining()];
                                        readBuff.get(bytes);
                                        String response = "client received! -> " + new String(bytes, StandardCharsets.UTF_8);
                                        System.out.println(response);
                                    }

                                    @Override
                                    public void failed(Throwable exc, ByteBuffer attachment) {
                                        try {
                                            socketChannel.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } finally {
                                            latch.countDown();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try {
                                socketChannel.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                latch.countDown();
                            }
                        }
                    });
                }

                @Override
                public void failed(Throwable exc, ClientHeart attachment) {
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
    }
}
