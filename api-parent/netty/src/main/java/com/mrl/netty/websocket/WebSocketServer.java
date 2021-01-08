package com.mrl.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @ClassName: WebSocketServer
 * @Description
 * @Author Mr.L
 * @Date 2021/1/8 17:55
 * @Version 1.0
 */
public class WebSocketServer {

    public static void main(String[] args) {
        new WebSocketServer().run(8080);
    }

    public void run(int port) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //将请求与应答消息编码或解码为http消息
                            pipeline.addLast("http-codec",new HttpServerCodec());
                            //将http消息的多个部分组合为一个完整的Http消息
                            pipeline.addLast("aggregator",new HttpObjectAggregator(65536));
                            //用来向客户端发送html5文件，主要用于支持浏览器与服务端进行websocket通信
                            pipeline.addLast("http-chunked",new ChunkedWriteHandler());
                            //最后添加WebSocket服务端Handler
                            pipeline.addLast("handler",new WebServerSocketHandler());
                        }
                    });
            Channel channel = bootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
