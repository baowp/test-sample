package com.iteye.baowp.nio.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * nio
 * Created by baowp on 15-1-10.
 */
public class TimeClientHandle implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TimeClientHandle.class);

    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public TimeClientHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }
    }

    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                if(selectionKeys.size()>0)
                    logger.debug("selector.selectedKeys() returns {} keys", selectionKeys.size());
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null)
                                key.channel().close();
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                System.exit(1);
            }
        }

        //when multiplexer closed,channel and pipe which registered on it will also be closed
        if (selector != null) {
            try {
                selector.close();
                logger.debug("selector closed");
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //check if successful
            SocketChannel sc = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                logger.debug("selectionKey isConnectable");
                if (sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc);
                } else {
                    System.exit(1);//connect failed,exit
                }
            }
            if (key.isReadable()) {
                logger.debug("selectionKey isReadable");
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    logger.info("now is: {}", body);
                    this.stop = true;
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                } else {
                    //read 0 byte,ignore
                }
            }
        }
    }

    private void doConnect() throws IOException {
        //if connected successful,then register on multiplexer to send message and receive messages
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            logger.debug("socketChannel prepare to register OP_READ");
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            logger.debug("socketChannel prepare to register OP_CONNECT");
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel sc) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            logger.info("send order to server succeed");
        } else {
            logger.info("writeBuffer has remaining");
        }
    }
}
