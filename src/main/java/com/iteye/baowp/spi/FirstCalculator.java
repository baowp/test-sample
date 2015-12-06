package com.iteye.baowp.spi;

import com.iteye.baowp.api.Calculator;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/13/13
 * Time: 9:57 AM
 */
public class FirstCalculator implements Calculator {
    @Override
    public String calculate(int i) {
        return getClass().getSimpleName();
    }
}
