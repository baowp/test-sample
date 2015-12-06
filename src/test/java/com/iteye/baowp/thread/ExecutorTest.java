package com.iteye.baowp.thread;

import com.iteye.baowp.domain.entity.BookEntity;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 1/24/14
 * Time: 1:00 PM
 */
public class ExecutorTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Executor executor = new ThreadPoolExecutor(4, 5, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10), /*new SynchronousQueue<Runnable>(),*/
            new ThreadPoolExecutor.CallerRunsPolicy());

    {
        executor = Executors.newFixedThreadPool(5);//Executors.newCachedThreadPool();//newSingleThreadExecutor();
    }

    @Test
    public void test() {
        logger.info("executor is {}", executor.getClass());
        List<BookEntity> list = getBooks();
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        long timeStart = System.currentTimeMillis();
        for (BookEntity book : list) {
            executor.execute(new Runner(countDownLatch, book));
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
        for (long i = 1; i <= 10; i++) {
            BookEntity book = new BookEntity();
            book.setId(i);
            list.add(book);
        }
        return list;
    }
}
