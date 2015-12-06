package com.iteye.baowp.netty5.chapter5.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by baowp on 15-1-16.
 */
public class EchoClientHandler1 extends ChannelHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private int counter;

    static final String ECHO_REQ = "Hi, Lilinfeng. Welcome to Netty.$_";

    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 10; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("This is {} times receive server: [{}]", ++counter, msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage());
        ctx.close();
    }
}
