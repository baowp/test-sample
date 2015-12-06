package com.iteye.baowp.netty.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/18/13
 * Time: 10:09 AM
 */
public class DiscardServerHandler extends SimpleChannelHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    public void channelConnected(
            ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.info("channelConnected");
        super.channelConnected(ctx,e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        Object object = e.getMessage();
        if (object instanceof ChannelBuffer) {
            ChannelBuffer buffer = (ChannelBuffer) object;
            StringBuilder sb = new StringBuilder();
            while (buffer.readable()) {
                sb.append((char)buffer.readByte());   //对双字节无效
            }
            logger.info("byte message string is: {}", sb.toString());

            logger.info("char message string is: {}", new String(buffer.array()));
        } else {
            throw new ClassCastException("message is not instanceof ChannelBuffer");
        }
        logger.info("discardServerHandler: {}", e.getMessage().toString());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();

        Channel ch = e.getChannel();
        ch.close();
    }
}
