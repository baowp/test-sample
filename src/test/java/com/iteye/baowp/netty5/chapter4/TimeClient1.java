package com.iteye.baowp.netty5.chapter4;

import com.iteye.baowp.netty5.chapter4.handler.TimeClientHandler1;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ws on 2015/1/14.
 */
public class TimeClient1 {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new TimeClientHandler1());
                        }
                    });
            logger.debug("发起异步连接操作");
            ChannelFuture f = b.connect(host, port).sync();

            logger.debug("等待客户端链路关闭");
            f.channel().closeFuture().sync();
            logger.debug("invoked f.channel().closeFuture().sync();");
        } finally {
            logger.debug("优雅退出，释放nio线程组");
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new TimeClient1().connect(9000, "127.0.0.1");
    }
}
