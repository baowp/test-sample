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
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                logger.info("invoked args {}", args);
                return "hello";
            }
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

        Calculator calculator = (Calculator) Proxy.newProxyInstance(Calculator.class.getClassLoader(),
                new Class[]{Calculator.class},
                new MyInvocationHandler(new FirstCalculator()));
        logger.info(calculator.calculate(1));
    }
}
