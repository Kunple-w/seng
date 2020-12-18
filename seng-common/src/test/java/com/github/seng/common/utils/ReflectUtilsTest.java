package com.github.seng.common.utils;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class ReflectUtilsTest {

    @Test
    public void generateExecutorSignature() throws Exception, RuntimeException {
        Method method = MethodUtils.getMatchingMethod(HelloService.class, "welcome", String.class, String[].class);

        ReflectUtils.generateExecutorSignature(method);
    }

    @Test
    public void generateExecutorSignature2() {
        Method method = MethodUtils.getMatchingMethod(ReflectUtilsTest.class, "generateExecutorSignature");

        ReflectUtils.generateExecutorSignature(method);
    }

    @Test
    public void testClass() {
        Method method = MethodUtils.getMatchingMethod(ReflectUtilsTest.class, "generateExecutorSignature");
        String generateExecutorSignature = ReflectUtils.generateExecutorSignature(method);
        Method method1 = ReflectUtils.getMethod(generateExecutorSignature);
        Assertions.assertEquals(method, method1);
    }

    @Test
    public void testInnerClass() {
        Method method = MethodUtils.getMatchingMethod(HelloService.class, "welcome");
        String generateExecutorSignature = ReflectUtils.generateExecutorSignature(method);
        Method method1 = ReflectUtils.getMethod(generateExecutorSignature);
        Assertions.assertEquals(method, method1);
    }


    public static class HelloService {
        String welcome(String msg, String[] msgs) {
            return "hello " + msg;
        }

        String welcome() {
            return "hello";
        }
    }
}