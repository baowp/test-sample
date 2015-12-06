package com.iteye.baowp.thread;

import com.iteye.baowp.domain.entity.BookEntity;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 3/21/14
 * Time: 6:00 PM
 */
public class FutureTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @Test
    public void test() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        Future<BookEntity> future = executor.submit(new Callable<BookEntity>() {
            @Override
            public BookEntity call() throws Exception {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                BookEntity book = new BookEntity();
                book.setId(1L);
                book.setTitle("future");
                //if(true)throw new IllegalArgumentException("run exception");
                logger.info("generated book {}",book.getId());
                return book;
            }
        });
        Thread.yield();
        logger.info("tasks have put in executor");
        try {
            BookEntity book = future.get();
            logger.info("book name is {}", book.getTitle());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
