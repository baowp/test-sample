package com.iteye.baowp.domain;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/3/13
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExecutorTest {

    public static void main(String[] args) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(300);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.afterPropertiesSet();
        for (int i = 0; i < 10; i++) {
            executor.execute(new Work(i));
        }
        executor.destroy();
        System.out.println("main method end");
    }

    private static class Work implements Runnable {

        private int id;

        private Work(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++)
                ;
            System.out.println(id);
        }
    }
}
