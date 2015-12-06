package com.iteye.baowp.netty.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/30/13
 * Time: 12:26 PM
 */
public class TimeClientHandler extends SimpleChannelHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        ChannelBuffer buffer = (ChannelBuffer) e.getMessage();

        ChannelBuffer buf = new DynamicChannelBuffer(64);
        buf.writeBytes(buffer);
        logger.info("dynamicBuffer readBytes {}", buf.readableBytes());

        if (buf.readableBytes() >= 4) {
            int in = buf.readInt();
            logger.info("receive int {}", in);
            long currentTimeMillis = in * 1000L;
            System.out.println(new Date(currentTimeMillis));
            e.getChannel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
