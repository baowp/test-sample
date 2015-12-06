package com.iteye.baowp.nio.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

/**
 * AIO
 * Created by baowp on 15-1-11.
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        if (this.channel == null) {
            this.channel = channel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String reg = new String(body, "UTF-8");
            logger.info("The time server receive order: {}", reg);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(reg) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
            doWrite(currentTime);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void doWrite(String currentTime) {
        logger.debug("prepare to write currentTime: {}", currentTime);
        byte[] bytes = currentTime.getBytes();
        final ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                logger.debug("doWrite anonymous class completed method");
                //continue send when didn't send complete
                if (buffer.hasRemaining()) {
                    logger.debug("buffer hasRemaining,continue to send");
                    channel.write(buffer, buffer, this);
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                logger.debug(exc.getMessage());
                try {
                    channel.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        logger.debug(exc.getMessage());
        try {
            channel.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
