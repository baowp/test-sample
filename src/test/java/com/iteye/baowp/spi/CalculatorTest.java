package com.iteye.baowp.spi;

import com.iteye.baowp.api.Calculator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/13/13
 * Time: 10:00 AM
 */

public class CalculatorTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testCalculate() {
        ServiceLoader<Calculator> serviceLoader = ServiceLoader.load(Calculator.class);
        for (Calculator calculator : serviceLoader) {
            logger.info(calculator.calculate(1));
        }
    }
}
