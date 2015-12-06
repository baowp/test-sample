package com.iteye.baowp.netty5.chapter4;

import com.iteye.baowp.netty5.chapter4.handler.TimeServerHandler2;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ws on 2015/1/14.
 */
public class TimeServer2 {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void bind(int port) throws Exception {
        //set nio thread group
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChildChannelHandler());
            logger.debug("绑定端口，同步等待成功");
            ChannelFuture f = b.bind(port).sync();

            logger.debug("invoke等待服务端监听端口关闭");
            f.channel().closeFuture().sync();
            logger.debug("invoked f.channel().closeFuture().sync();");
        } finally {
            logger.debug("优雅退出，释放线程池资源");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new TimeServerHandler2());
        }
    }

    public static void main(String[] args) throws Exception {
        new TimeServer2().bind(9000);
    }
}
