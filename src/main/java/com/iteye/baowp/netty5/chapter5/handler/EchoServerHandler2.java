package com.iteye.baowp.netty5.chapter5.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by baowp on 15-1-16.
 */
public class EchoServerHandler2 extends ChannelHandlerAdapter {

    private final Logger logger= LoggerFactory.getLogger(getClass());

    public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
        logger.info("Receive client: [{}]",msg);
    }

    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        logger.error(cause.getMessage());
        ctx.close();
    }
}
