package com.iteye.baowp.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by baowp on 2017/3/7.
 */
public class ExecutorUtils {

    public static ThreadPoolExecutor newThreadPoolExecutor(){
        return newThreadPoolExecutor("pool-");
    }

    public static ThreadPoolExecutor newThreadPoolExecutor(String threadPoolPrefix){
        return newThreadPoolExecutor(5,8,60,100,threadPoolPrefix);
    }

    public static ThreadPoolExecutor newThreadPoolExecutor(int corePoolSize,
                                                           int maximumPoolSize,
                                                           long keepAliveSeconds,
                                                           int queueCapacity,
                                                           String threadPoolPrefix){
        return  new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveSeconds, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(queueCapacity), /*new SynchronousQueue<Runnable>(),*/
                new DefaultThreadFactory(threadPoolPrefix),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory(String poolPrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = poolPrefix +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
