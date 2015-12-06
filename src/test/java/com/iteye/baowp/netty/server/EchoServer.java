package com.iteye.baowp.netty.server;

import com.iteye.baowp.netty.handler.EchoServerHandler;
import com.iteye.baowp.netty.handler.HeartbeatHandler;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.CharsetUtil;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;


/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/18/13
 * Time: 10:30 AM
 */
public class EchoServer {

    public static void main(String... args) {
        Timer timer = new HashedWheelTimer();
        ChannelHandler idleStateHandler = new IdleStateHandler(timer, 5, 5, 5);

        ChannelPipeline pipeline = serve().getPipeline();
//        pipeline.addLast("idleStateHandler", idleStateHandler);
//        pipeline.addLast("heartbeatHandler", new HeartbeatHandler());
        //pipeline.addLast("framer",new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter()));
        //pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1024,0,1)); // get header from message
        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("handler", new EchoServerHandler());
    }

    static ServerBootstrap serve() {
        ChannelFactory factory =
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool());

        ServerBootstrap bootstrap = new ServerBootstrap(factory);

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        bootstrap.bind(new InetSocketAddress(9000));

        return bootstrap;
    }

}
