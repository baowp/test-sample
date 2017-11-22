package com.iteye.baowp.guava.future;

import com.google.common.util.concurrent.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by baowp on 2017/11/21.
 */
public class ListenableFutureTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @Test
    public void test() {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(executor);
        ListenableFuture<Explosion> explosion = service.submit(new Callable<Explosion>() {
            public Explosion call() {
                return pushBigRedButton();
            }
        });

        Futures.addCallback(explosion, new FutureCallback<Explosion>() {
            // we want this handler to run immediately after we push the big red button!
            public void onSuccess(Explosion explosion) {
                walkAwayFrom(explosion);
            }

            public void onFailure(Throwable thrown) {
                battleArchNemesis(thrown); // escaped the explosion!
            }
        }, executor);
    }

    private Explosion pushBigRedButton() {
        logger.info("pushBigRedButton");
        return new Explosion();
    }

    private void walkAwayFrom(Explosion explosion) {
        logger.info("walkAwayFrom explosion: {}", explosion);
    }

    private void battleArchNemesis(Throwable thrown) {
        logger.error("battleArchNemesis", thrown);
    }
}
