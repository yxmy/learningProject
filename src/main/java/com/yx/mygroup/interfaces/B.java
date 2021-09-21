package com.yx.mygroup.interfaces;

/**
 * Created by yuanxin on 2021/9/6.
 */
public interface B extends A {
    default void hello() {
        System.out.println("Hello，B");
    }
}
