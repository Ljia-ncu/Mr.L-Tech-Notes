package com.mrl.netty.grace;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName: NettyServerhandler
 * @Description
 * @Author Mr.L
 * @Date 2021/1/7 20:02
 * @Version 1.0
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        String request = "server received: -> " + new String(bytes, StandardCharsets.UTF_8);
        System.out.println(request);

        ByteBuf response = Unpooled.copiedBuffer("server: i'm ok".getBytes());
        ctx.write(response);*/

        //由于在handler中配置了StringDecoder，所以这里的msg直接被解码成String
        String req = (String) msg;
        String request = "server received: -> " + req;
        System.out.println(request);

        //应答时也带上_$，让客户端进行解码,解决服务端应答粘包问题
        ByteBuf response = Unpooled.copiedBuffer("server: i'm ok _$".getBytes());
        ctx.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
