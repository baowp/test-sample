package com.iteye.baowp.netty5.chapter14;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by baowp on 15-1-25.
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {
    private final Logger logger= LoggerFactory.getLogger(getClass());

    public void channelRead(ChannelHandlerContext ctx,Object msg){
        NettyMessage message=(NettyMessage)msg;
        //返回心跳应答消息
        if(message.getHeader()!=null&&message.getHeader().getType()==MessageType.HEARTBEAT_REQ.value()){
            logger.info("Receive client heart beat message: ---> {}",message);
            NettyMessage heartBeat=buildHeartBeat();
            logger.info("Send heart beat response message to client: ---> {}",heartBeat);
            ctx.writeAndFlush(heartBeat);
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeartBeat(){
        NettyMessage message=new NettyMessage();
        Header header=new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }
}
