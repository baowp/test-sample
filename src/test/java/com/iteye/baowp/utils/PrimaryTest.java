package com.iteye.baowp.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 1/14/14
 * Time: 9:37 AM
 */
public class PrimaryTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test() {
        char c = (char) 38;
        System.out.println(c);

        int i = 0xb1;
        System.out.println(i);

        String data = "<?xml ?> <data>text</data>";
        data = data.replaceFirst("<\\?.+\\?>", "");
        System.out.println(data);
    }

    @Test
    public void testStringFormat() {
        String str = String.format("Unsupported value %s for feature [%s]", "first", "second");
        System.out.println(str);
    }

    @Test
    public void testDate() {
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
        System.out.println(date.getTime());
        System.out.println(Integer.MAX_VALUE);
    }

    /**
     * 无符号右移高位补0,只对32位与64位数有效
     */
    @Test
    public void testNumber() {
        logger.info("min int is {}", Integer.MIN_VALUE);
        String str = "10000000000000000000000000000011";
        int i = (int) Long.parseLong(str, 2) >> 0;
        logger.info("number is {}", i);
        logger.info("number binary is {}", Integer.toBinaryString(i));
    }

    @Test
    public void testStackTrace(){
        System.out.println(new Throwable().getStackTrace()[0].getLineNumber());
        System.out.println(Thread.currentThread().getStackTrace()[1].getLineNumber());
    }
}
