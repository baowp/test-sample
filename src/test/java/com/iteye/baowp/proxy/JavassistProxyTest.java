package com.iteye.baowp.proxy;

import com.iteye.baowp.api.Calculator;
import com.iteye.baowp.spi.FirstCalculator;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/17/13
 * Time: 9:45 AM
 */
public class JavassistProxyTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testCalculator() {
        Calculator calculator = create(FirstCalculator.class);
        logger.info(calculator.calculate(1));
        logger.info(calculator.getClass().getName());
    }

    @Test
    public void testEfficiency(){
        Calculator calculator = create(FirstCalculator.class);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            calculator.calculate(i);
        }
        long end = System.currentTimeMillis();
        long span = end - start;
        logger.info("span time {}", span);
    }

    @Test
    public void testArrayList() {
        List list = create(ArrayList.class);
        list.add("foo");
        logger.info(list.getClass().getName());
    }

    private <T> T create(Class<T> classs) {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(classs);
        Class clazz = factory.createClass();
        MethodHandler handler = new MethodHandler() {
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                //logger.info("proxy handle {},{}", thisMethod.getName(), proceed.getName());
                return proceed.invoke(self, args);
            }
        };
        Object instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ((ProxyObject) instance).setHandler(handler);
        return (T) instance;
    }
}
