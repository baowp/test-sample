package com.iteye.baowp.netty.handler;

import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/18/13
 * Time: 4:21 PM
 */
public class EchoServerHandler extends SimpleChannelHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        String inStr;
        if (e.getMessage() instanceof String)
            inStr = (String) e.getMessage();
        else {
            ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
            inStr = new String(buffer.array());
        }
        logger.info("received message string is: {}", inStr);

        Channel ch = e.getChannel();
        String outStr = "{" + inStr + "]";
        ChannelBuffer sendBuffer = new BigEndianHeapChannelBuffer(outStr.getBytes());
        sendBuffer.setByte(0, '[');
        ch.write(sendBuffer);

        Channel channel = ctx.getChannel();
        Assert.isTrue(ch == channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();

        Channel ch = e.getChannel();
        ch.close();
    }
}
