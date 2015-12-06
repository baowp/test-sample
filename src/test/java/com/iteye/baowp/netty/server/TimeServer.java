package com.iteye.baowp.netty.server;

import com.iteye.baowp.netty.handler.TimeServerHandler;
import org.jboss.netty.channel.ChannelPipeline;

import static com.iteye.baowp.netty.server.EchoServer.serve;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/30/13
 * Time: 12:19 PM
 */
public class TimeServer {
    public static void main(String... args) {
        ChannelPipeline pipeline = serve().getPipeline();
        pipeline.addLast("handler", new TimeServerHandler());
    }
}
