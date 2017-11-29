package com.iteye.baowp.thread;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by baowp on 2017/11/28.
 */
public class LockTest {
    @Test
    public void test() {
        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadExecute(business, "sub");
            }
        }).start();
        threadExecute(business, "main");
    }
    private void threadExecute(Business business, String threadType) {
        for(int i = 0; i < 100; i++) {
            try {
                if("main".equals(threadType)) {
                    business.main(i);
                } else {
                    business.sub(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Business {
    private boolean bool = true;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public /*synchronized*/ void main(int loop) throws InterruptedException {
        lock.lock();
        try {
            while(bool) {
                condition.await();//this.wait();
            }
            for(int i = 0; i < 1; i++) {
                System.out.println("main thread seq of " + i + ", loop of " + loop);
            }
            bool = true;
            condition.signal();//this.notify();
        } finally {
            lock.unlock();
        }
    }
    public /*synchronized*/ void sub(int loop) throws InterruptedException {
        lock.lock();
        try {
            while(!bool) {
                condition.await();//this.wait();
            }
            for(int i = 0; i < 1; i++) {
                System.out.println("sub thread seq of " + i + ", loop of " + loop);
            }
            bool = false;
            condition.signal();//this.notify();
        } finally {
            lock.unlock();
        }
    }
}
