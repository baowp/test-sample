package com.iteye.baowp.netty5.chapter14;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by baowp on 15-1-25.
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        NettyMessage message = (NettyMessage) msg;

        //check if the message is the response of handshake.
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            byte loginResult = (Byte) message.getBody();
            if (loginResult != (byte) 0) {//handshake failed
                ctx.close();
            } else {
                logger.info("Login is ok: {}", message);
                ctx.fireChannelRead(msg);
            }
        } else
            ctx.fireChannelRead(msg);
    }

    private NettyMessage buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    /**
     * written on http://m.blog.csdn.net/blog/ITer_ZC/39317311
     *
     * @param ctx
     * @throws Exception
     */
   /* public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }*/
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.fireExceptionCaught(cause);
    }
}
