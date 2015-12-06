package com.iteye.baowp.nio.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * Created by baowp on 15-1-11.
 */
public class AsyncTimeClientHandler implements CompletionHandler<Void, AsyncTimeClientHandler>, Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AsynchronousSocketChannel client;
    private String host;
    private int port;
    private CountDownLatch latch;
    private int count = 1;

    public AsyncTimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            client = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(count);
        logger.debug("client.connect(new InetSocketAddress(host, port), this, this);");
        client.connect(new InetSocketAddress(host, port), this, this);
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            client.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
        for (int i = 0; i < count; i++) {
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }*/
            clientRequest();
        }
    }

    private void clientRequest() {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        logger.debug("client.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>(){} ");
        client.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                if (buffer.hasRemaining()) {
                    logger.debug("buffer hasRemaining,client.write(buffer,buffer,this)");
                    client.write(buffer, buffer, this);
                } else {
                    logger.debug("client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {}");
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer buffer) {
                            logger.debug("client.read anonymous class completed method");
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            String body;
                            try {
                                body = new String(bytes, "UTF-8");
                                logger.info("Now is: {}", body);
                                latch.countDown();
                            } catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), e);
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            logger.debug(exc.getMessage());
                            try {
                                client.close();
                                latch.countDown();
                            } catch (IOException e) {
                                logger.error(e.getMessage(), e);
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                logger.debug(exc.getMessage());
                try {
                    client.close();
                    latch.countDown();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
        logger.error(exc.getMessage(), exc);
        try {
            client.close();
            latch.countDown();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
