package com.iteye.baowp.netty5.chapter6;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * Created by baowp on 15-1-17.
 */
public class UserInfoTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testLength() throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserId(100).buildUserName("Welcome to Netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(info);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        logger.info("The jdk Serializable length is: {}", b.length);
        bos.close();

        logger.info("The byte array serializable length is: {}", info.codeC().length);
    }

    @Test
    public void testTime() throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserId(100).buildUserName("Welcome to netty");
        int loop = 1000000;
        ByteArrayOutputStream bos;
        ObjectOutputStream os;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(info);
            os.flush();
            os.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }
        long endTime = System.currentTimeMillis();
        logger.info("The jdk serializable cost time is: {} ms", endTime - startTime);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[] b = info.codeC(buffer);
        }
        endTime = System.currentTimeMillis();
        logger.info("The byte array serializable cost time is: {} ms", endTime - startTime);
    }
}
