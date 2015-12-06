package com.iteye.baowp.netty5.chapter7;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by baowp on 15-1-17.
 */
public class SubReqClientHandler extends ChannelHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReq subReq(int i) {
        SubscribeReq req = new SubscribeReq();
        req.setAddress("南京市江宁区方山国家地质公园");
        req.setPhoneNumber("138xxxxxxxx");
        req.setProductName("Netty权威指南");
        req.setSubReqId(i);
        req.setUserName("Lilinfeng");
        return req;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("Receive server response:[{}]", msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage());
        ctx.close();
    }
}
