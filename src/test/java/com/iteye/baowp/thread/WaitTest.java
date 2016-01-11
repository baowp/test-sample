package com.iteye.baowp.thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 20151022 on 2016/1/11.
 */
public class WaitTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test() {
        Calculator calculator = new Calculator();
        calculator.start();
        synchronized (calculator) {
            logger.info("run into synchronized block");
            try {
                while (!calculator.calculated)
                calculator.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("main thread continues");
        }
        logger.info("application exits");
    }

    class Calculator extends Thread {
        int total;
        boolean calculated;
        public void run() {
            try {
                Thread.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 1; i <= 100; i++) {
                total += i;
            }
            logger.info("total is {}", total);
            calculated=true;
            //notify();
        }
    }
}
