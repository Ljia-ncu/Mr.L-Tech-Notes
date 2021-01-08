package com.mrl.netty.grace;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @ClassName: NettyClientHanddler
 * @Description
 * @Author Mr.L
 * @Date 2021/1/7 21:15
 * @Version 1.0
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道连接上后就发送一条消息到服务端
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //注意这里，消息中加入了_$
        byte[] bytes = "client: are u ok _$".getBytes();
        //这里用来测试消息粘包，如过服务端只收到<100条消息，并返回<100条应答，则接收与应答都发生了粘包；
        for (int i = 0; i < 100; i++) {
            ByteBuf byteBuf = Unpooled.buffer(bytes.length);
            byteBuf.writeBytes(bytes);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        System.out.println("client received: -> " + new String(bytes));*/

        System.out.println("client received: -> " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
