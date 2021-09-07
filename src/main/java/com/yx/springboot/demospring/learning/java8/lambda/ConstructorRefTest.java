package com.yx.springboot.demospring.learning.java8.lambda;

import com.yx.springboot.demospring.model.User;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 构造器应用
 * @author Yuan_xin
 */
public class ConstructorRefTest {

    public void test1() {
        Supplier<User> supplier = User::new;
    }

    public void test2() {
        Function<String, User> function = User::new;
    }

    public void test3() {
        BiFunction<String, Integer, User> biFunction = User::new;
    }
}
