package com.iteye.baowp.spi;

import com.iteye.baowp.api.Calculator;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/13/13
 * Time: 10:09 AM
 */
public class SecondCalculator implements Calculator {

    @Override
    public String calculate(int i) {
        return getClass().getSimpleName();
    }
}
