package com.mrl.netty.grace;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @ClassName: NettyClient
 * @Description
 * @Author Mr.L
 * @Date 2021/1/7 19:15
 * @Version 1.0
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        new NettyClient().connect("127.0.0.1", 8080);
    }

    private void connect(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    //客户端使用的channel是NioSocketChannel
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("_$".getBytes())));
                            channel.pipeline().addLast(new StringDecoder());
                            channel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            //连接到服务端，同步等待连接成功
            ChannelFuture future = bootstrap.connect(host, port).sync();
            //Wait until the server socket is closed.
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
