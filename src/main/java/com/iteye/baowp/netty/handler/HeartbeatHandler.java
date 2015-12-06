package com.iteye.baowp.netty.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 1/21/14
 * Time: 3:20 PM
 */
public class HeartbeatHandler extends IdleStateAwareChannelHandler {

    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) {
        if (e.getState() == IdleState.READER_IDLE) {
            System.out.println("Reader idle, closing channel");
            //e.getChannel().close();
            e.getChannel().write("heartbeat-reader_idle");
        }
        else if (e.getState() == IdleState.WRITER_IDLE) {
            System.out.println("Writer idle, sending heartbeat");
            e.getChannel().write("heartbeat-writer_idle");
        }
        else if (e.getState() == IdleState.ALL_IDLE) {
            System.out.println("All idle, sending heartbeat");
            e.getChannel().write("heartbeat-all_idle");
        }
    }
}