package com.iteye.baowp.nio.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * Created by baowp on 15-1-11.
 */
public class AsyncTimeServerHandler implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            logger.info("The time server is start in port: {}", port);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void doAccept() {
        logger.debug("asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());");
        asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
    }
}
