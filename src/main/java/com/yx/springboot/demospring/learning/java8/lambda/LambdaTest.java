package com.yx.springboot.demospring.learning.java8.lambda;

import org.junit.Test;

import java.util.Comparator;

/**
 * @author Yuan_xin
 */
public class LambdaTest {

    @Test
    public void test1() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("这是一个runnable");
            }
        };
        runnable.run();

        System.out.println("************************");

        Runnable runnable1 = () -> System.out.println("这是另外一个runnable");
        runnable1.run();
    }

    @Test
    public void test2() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        System.out.println(comparator.compare(12, 21));

        System.out.println("*******************************");

        //Lambda表达式
        Comparator<Integer> comparator1 = (o1, o2) -> Integer.compare(o1, o2);
        System.out.println(comparator1.compare(31, 21));

        System.out.println("********************************");
        Comparator<Integer> comparator2 = Integer::compareTo;
        System.out.println(comparator2.compare(31, 21));

    }
}
