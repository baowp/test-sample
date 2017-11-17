package com.iteye.baowp.guava;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.ChangeEvent;
import java.util.concurrent.Executors;

/**
 * Created by baowp on 2017/11/17.
 */
public class AsyncEventBusTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    EventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(2),
            (Throwable exception, SubscriberExceptionContext context) -> {
                logger.info(exception.getMessage(), exception);
                logger.info(context.toString());
            });

    @Before
    public void register() {
        logger.info("eventBus.register");
        eventBus.register(new EventBusChangeRecorder());
    }

    @Test
    public void changeCustomer() {
        logger.info("changeCustomer");
        ChangeEvent event = getChangeEvent();
        eventBus.post(event);

        eventBus.post(new Object());
    }

    private ChangeEvent getChangeEvent() {
        String str = "abc";
        ChangeEvent event = new ChangeEvent(str);
        return event;
    }
}
