package com.iteye.baowp.thread;

import com.iteye.baowp.domain.entity.BookEntity;
import com.iteye.baowp.utils.ExecutorUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 1/24/14
 * Time: 1:00 PM
 */
public class ExecutorTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10), /*new SynchronousQueue<Runnable>(),*/
            new DefaultThreadFactory("executor"),
            new ThreadPoolExecutor.CallerRunsPolicy());

    {
        executor = ExecutorUtils.newThreadPoolExecutor("executor");
    }

    @Test
    public void test() {
        logger.info("executor is {}", executor.getClass());
        List<BookEntity> list = getBooks();
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        long timeStart = System.currentTimeMillis();
        for (BookEntity book : list) {
            executor.execute(new Runner(countDownLatch, book));
          //  logger.info("activeCount:{},largestPoolSize:{},completedTaskCount:{}",executor.getActiveCount(),executor.getLargestPoolSize(),executor.getCompletedTaskCount());
        }
        try {
            countDownLatch.await();  //  main thread wait to prevent threadPool doesn't execute all threads but processor exit
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("cost time is {}ms", (timeEnd - timeStart));
    }


    private class Runner implements Runnable {

        private CountDownLatch countDownLatch;
        private BookEntity book;

        private Runner(CountDownLatch countDownLatch, BookEntity book) {
            this.countDownLatch = countDownLatch;
            this.book = book;
        }

        @Override
        public void run() {
            try {
                logger.info("when countDownLatch={} runs book {}", countDownLatch.getCount(), book.getId());
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    private List<BookEntity> getBooks() {
        List<BookEntity> list = new ArrayList<BookEntity>();
        for (long i = 1; i <= 100; i++) {
            BookEntity book = new BookEntity();
            book.setId(i);
            list.add(book);
        }
        return list;
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
