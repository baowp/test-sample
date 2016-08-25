package com.iteye.baowp.thread;

import org.junit.Test;

/**
 * Created by baowp on 2016/8/25.
 */
public class ReorderingTest {

    @Test
    public void testReordering() {
        for (int i = 1; i <= 1000000; i++) {
            System.out.println(i + "times");
            new Handler().work();
        }
    }

    private class Handler {
        int x;
        boolean bExit;

        void work() {
            Thread t1 = new Thread(() -> {
                x = 1;
                bExit = true;
            });
            Thread t2 = new Thread(() -> {
                if (bExit) {
                    System.out.println("x=" + x);
                }
            });
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
