package com.iteye.baowp.guava;

import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.ChangeEvent;
import java.util.concurrent.TimeUnit;

/**
 * Created by baowp on 2017/11/16.
 */
public class EventBusTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    EventBus eventBus = new EventBus();

    @Before
    public void register() {
        logger.info("eventBus.register");
        eventBus.register(new EventBusChangeRecorder());
    }

    @Test
    public void changeCustomer() {
        logger.info("changeCustomer");
        /*try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        ChangeEvent event = getChangeEvent();
        eventBus.post(event);
    }

    private ChangeEvent getChangeEvent() {
        String str = "abc";
        ChangeEvent event = new ChangeEvent(str);
        return event;
    }
}
