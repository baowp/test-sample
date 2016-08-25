package com.iteye.baowp.thread;

import com.iteye.baowp.domain.entity.BookEntity;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 3/19/14
 * Time: 5:14 PM
 */
public class ThreadLocalTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<BookEntity> bookLocal = new ThreadLocal<BookEntity>() {
        protected BookEntity initialValue() {
            return new BookEntity();
        }
    };

    public static void main(String args[]){
        BookEntity book = bookLocal.get();
        book.setTitle("Java");
        invoke();
    }

    private static void invoke(){
        BookEntity book = bookLocal.get();
        System.out.println(book.getTitle());
    }

    @Test
    public void test() {
        Executor executor = Executors.newFixedThreadPool(5);
        int size = 6;
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++)
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        BookEntity book = bookLocal.get();
                        for (int i = 0; i < 50; i++) {
                            if (book.getId() == null) book.setId(1l);
                            else book.setId(book.getId() + 1);
                        }
                        logger.info("{}", book.getId());
                        continueCount();
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("main thread over");
    }

    private void continueCount() {
        BookEntity book = bookLocal.get();
        for (int i = 0; i < 50; i++) {
            if (book.getId() == null) book.setId(1l);
            else book.setId(book.getId() + 1);
        }
        logger.info("{}", book.getId());
    }
}
