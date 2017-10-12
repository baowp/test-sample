package com.iteye.baowp.thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by 20151022 on 2016/1/11.
 */
public class WaitTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int poolSize = 2;
    private Executor executor = Executors.newFixedThreadPool(poolSize);

    @Test
    public void test() {
        Calculator calculator = new Calculator();
        for (int i = 0; i < poolSize; i++)
            new Thread(() -> {
                synchronized (calculator) {
                    logger.info("1run into synchronized block");
                    try {
                        while (!calculator.calculated) {
                            logger.info("2Begin to wait");
                            calculator.wait();
                            logger.info("5Current waiting is finished");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("7thread pool finished");
                }
            }).start();
        calculator.start();
        sleep2(5000);
        logger.info("application exits");
    }

    class Calculator extends Thread {
        int total;
        boolean calculated;

        public void run() {
            sleep2(50);
            for (int i = 1; i <= 100; i++) {
                total += i;
            }
            logger.info("1total is {}", total);
            synchronized (this) {
                logger.info("3begin to notify");
                notify();
                logger.info("3has notified");
            }
            sleep2(1000);
            logger.info("4has notified and quit sync");
            calculated = true;
        }
    }

    private void sleep2(long milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
