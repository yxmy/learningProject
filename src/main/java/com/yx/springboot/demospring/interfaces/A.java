package com.yx.springboot.demospring.interfaces;

/**
 * Created by yuanxin on 2021/9/6.
 */
public interface A {

    default void hello() {
        System.out.println("Hello，A");
    }
}
