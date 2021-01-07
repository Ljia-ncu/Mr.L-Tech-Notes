package com.mrl.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: AioServer
 * @Description
 * @Author Mr.L
 * @Date 2021/1/7 13:54
 * @Version 1.0
 */
public class AioServer {
    public static void main(String[] args) throws InterruptedException {
        new AioServer().start(8080);
        Thread.currentThread().join();
    }

    public void start(int port) {
        new Thread(new ServerHeart(port)).start();
    }

    private class ServerHeart implements Runnable {

        private CountDownLatch latch;
        private AsynchronousServerSocketChannel serverSocketChannel;

        public ServerHeart(int port) {
            try {
                //绑定端口
                serverSocketChannel = AsynchronousServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress(port));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            latch = new CountDownLatch(1);
            doAccept();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void doAccept() {
            //这里的CompletionHandler是用来处理accpet这个操作的回调
            //attachment=this 这里是用来传递当前类
            serverSocketChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, ServerHeart>() {

                //completed回调方法 表示连接成功，这里的AsynchronousSocketChannel就是与客户端连接的通道
                @Override
                public void completed(AsynchronousSocketChannel result, ServerHeart attachment) {
                    //注意这里，在外部serverSocketChannel已经连接成功了
                    //这里再次进行accept,主要是为了形成一个循环，因为服务端连接的肯定不止一个客户端
                    attachment.serverSocketChannel.accept(attachment, this);

                    //下面就是模板化的channel通信操作
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    //这里的CompletionHandler是用来处理read这个操作的回调
                    result.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer len, ByteBuffer attachment) {
                            attachment.flip();
                            byte[] bytes = new byte[attachment.remaining()];
                            attachment.get(bytes);
                            String response = "server received! -> " + new String(bytes, StandardCharsets.UTF_8);
                            System.out.println(response);
                            doWrite(result);
                        }

                        private void doWrite(AsynchronousSocketChannel sc) {
                            byte[] bytes = "server: i'm ok.".getBytes();
                            ByteBuffer writeBuff = ByteBuffer.allocate(bytes.length);
                            writeBuff.put(bytes);
                            writeBuff.flip();
                            //write也可以设置一个CompletionHandler,最好单独建一个类impl CompletionHandler
                            sc.write(writeBuff);
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try {
                                result.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void failed(Throwable exc, ServerHeart attachment) {
                    exc.printStackTrace();
                    attachment.latch.countDown();
                }
            });
        }
    }
}
