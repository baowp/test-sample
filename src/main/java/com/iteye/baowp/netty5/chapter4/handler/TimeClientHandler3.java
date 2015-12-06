package com.iteye.baowp.netty5.chapter4.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ws on 2015/1/14.
 */
public class TimeClientHandler3 extends ChannelHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private byte[] req;
    private int counter;

    public TimeClientHandler3() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    public void channelActive(ChannelHandlerContext ctx) {
        logger.debug("client channelActive");
        for (int i = 0; i < 100; i++) {
            ByteBuf message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        logger.info("Now is: {} ; the counter is: {}", body, ++counter);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Unexpected exception from downstream: {}", cause.getMessage());
        ctx.close();
    }
}
