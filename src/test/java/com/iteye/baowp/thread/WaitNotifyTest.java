package com.iteye.baowp.thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 3/21/14
 * Time: 7:29 PM
 */
public class WaitNotifyTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ExecutorService executor = Executors.newFixedThreadPool(5);

    @Test
    public void test() {
        Map<Future, Future> map = new HashMap<Future, Future>();
        final QueueBuffer q = new QueueBuffer();
        for (int i = 1; i <= 10; i++) {
            final int j = i;
            Future future = executor.submit(new Runnable() {
                @Override
                public void run() {
                    q.get();
                }
            });
            Future future2 = executor.submit(new Callable() {
                @Override
                public Object call() {
                    q.put(j);
                    return j;
                }
            });
            map.put(future, future2);
        }
        for (Map.Entry<Future, Future> entry : map.entrySet()) {
            try {
                logger.info("key:{},value:{}", entry.getKey().get(), entry.getValue().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private class QueueBuffer {
        int num;
        boolean set;

        synchronized int get() {
            if (!set) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("get: {}", num);
            set = false;
            notify();
            return num;
        }

        synchronized void put(int n) {
            if (set) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            num = n;
            set = true;
            logger.info("put: {}", num);
            notify();
        }
    }
}
