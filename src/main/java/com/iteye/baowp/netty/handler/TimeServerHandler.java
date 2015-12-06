package com.iteye.baowp.netty.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/30/13
 * Time: 11:05 AM
 */
public class TimeServerHandler extends SimpleChannelHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        Channel ch = e.getChannel();

        ChannelBuffer time = ChannelBuffers.buffer(4);

        int message= ((int) (System.currentTimeMillis() / 1000L));
        logger.info("channelConnected,wrote {} as message", message);
        time.writeInt(message);
//        time.writeShort(message>>16&0xff);
//        time.writeShort(message&0xff);

        ChannelFuture f = ch.write(time); //will waiting until client received

        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
