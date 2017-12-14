package com.iteye.baowp.proxy;

import com.iteye.baowp.api.Calculator;
import com.iteye.baowp.spi.FirstCalculator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/16/13
 * Time: 3:32 PM
 */
public class JdkProxyTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testCalculate() {
        InvocationHandler handler = (Object proxy, Method method, Object[] args) -> {
            logger.info("invoked method {} args {}", method, args);
            return "hello";
        };
        Class proxyClass = Proxy.getProxyClass(Calculator.class.getClassLoader(), new Class[]{Calculator.class});
        try {
            Calculator calculator = (Calculator) proxyClass.getConstructor(new Class[]{InvocationHandler.class}).
                    newInstance(new Object[]{handler});
            logger.info(calculator.calculate(0));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFirstCalculator() {
        Calculator calculator = getCalculator();
        logger.info(calculator.calculate(1));
    }

    private Calculator getCalculator() {
        class MyInvocationHandler implements InvocationHandler {
            private final Object instance;

            private MyInvocationHandler(Object instance) {
                this.instance = instance;
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(instance, args);
            }
        }

        return (Calculator) Proxy.newProxyInstance(Calculator.class.getClassLoader(),
                new Class[]{Calculator.class},
                new MyInvocationHandler(new FirstCalculator()));
    }

    @Test
    public void testEfficiency() {
        Calculator calculator = getCalculator();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            calculator.calculate(i);
        }
        long end = System.currentTimeMillis();
        long span = end - start;
        logger.info("span time {}", span);
    }
}
