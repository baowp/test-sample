package com.iteye.baowp.guava;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.ChangeEvent;

/**
 * Created by baowp on 2017/11/16.
 */
public class EventBusChangeRecorder {
    private final Logger logger= LoggerFactory.getLogger(getClass());
    @Subscribe
    public void recordCustomerChange(ChangeEvent e) {
        recordChange(e);
    }

    private void recordChange(ChangeEvent e) {
        logger.info(e.toString());
    }

    @Subscribe
    public Object doSomething(Object e){
        logger.info(e.toString());
        return e;
    }
}
