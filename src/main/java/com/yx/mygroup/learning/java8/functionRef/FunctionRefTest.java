package com.yx.mygroup.learning.java8.functionRef;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author yuanxin
 * @date 2021/9/3
 */
public class FunctionRefTest {

    /**
     * 自定义的函数式接口抛出异常
     */
    @Test
    public void test1() {
        BufferedReaderProcessor p = (BufferedReader bufferedReader) -> bufferedReader.readLine();
    }

    /**
     * 在lambda中显式的捕获
     */
    @Test
    public void test2() {
        Function<BufferedReader, String> fun = (BufferedReader buffered) -> {
            try {
                return buffered.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Test
    public void test3() {

        Predicate<String> containsA = (String string) -> {
            return this.containsA(string);
        };

        Predicate<String> predicate1 = this::containsA;
    }

    public boolean containsA(String str) {
        return str.contains("a");
    }
}
