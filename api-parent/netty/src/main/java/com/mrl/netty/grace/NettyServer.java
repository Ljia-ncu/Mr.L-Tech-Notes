package com.mrl.netty.grace;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @ClassName: NettyServer
 * @Description
 * @Author Mr.L
 * @Date 2021/1/7 19:15
 * @Version 1.0
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        final int port = 8080;
        new NettyServer().bind(port);
    }

    public void bind(int port) {
        //Reactor线程组，一个用于服务端接收客户端的连接，一个用于进行SocketChannel网络读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //用于启动NIO服务端的辅助启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    //服务端使用的channel是NioServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    //配置Tcp的sync_queue长度，用于存储tcp三次握手的已连接+未连接
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //类似Reactor模式的handler类，用于处理网络IO事件
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //将事件处理器ChannelHandler添加到ChannelPipeline中
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //添加一个按特殊字符进行拆包的解码器
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("_$".getBytes())));
                            //将ByteBuf内容转为String，所以后面的handler获取到的消息是String类型
                            socketChannel.pipeline().addLast(new StringDecoder());
                            //这里添加消息读取/发送handler
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            //绑定端口,同步等待绑定成功
            ChannelFuture future = bootstrap.bind(port).sync();
            //Wait until the server socket is closed.
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
