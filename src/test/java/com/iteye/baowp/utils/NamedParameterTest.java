package com.iteye.baowp.utils;

import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.LocalVariable;
import com.sun.org.apache.bcel.internal.classfile.LocalVariableTable;
import org.junit.Test;
import org.springframework.beans.PropertyValues;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
//import java.lang.reflect.Parameter;

/**
 * Created by baowp on 2015/3/28.
 */
public class NamedParameterTest {

   /* @Test
    public void testJava8() throws NoSuchMethodException {
        Method method = org.springframework.validation.DataBinder.class.getDeclaredMethod("bind", new Class[]{PropertyValues.class});
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println(parameter.getName());
        }
    }*/

    @Test
    public void testJavaBelow() throws NoSuchMethodException {
        Method method = org.springframework.validation.DataBinder.class.getDeclaredMethod("bind", new Class[]{PropertyValues.class});
        com.sun.org.apache.bcel.internal.classfile.Method bcelMethod =
                Repository.lookupClass(method.getDeclaringClass()).getMethod(method);
        LocalVariableTable localVariableTable = bcelMethod.getLocalVariableTable();
        String[] parameterNames = new String[method.getParameterTypes().length];
        int offset = Modifier.isStatic(method.getModifiers()) ? 0 : 1;
        for (int i = 0; i < parameterNames.length; i++) {
            parameterNames[i] = localVariableTable.getLocalVariable(i + offset).getName();
        }
        for (String parameterName : parameterNames) {
            System.out.println(parameterName);
        }
    }

    public void testSpring(){
      //  ParameterNameDiscoverer parameterNameDiscoverer=new DefaultParamet
    }
}
