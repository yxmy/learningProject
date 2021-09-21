package com.yx.mygroup.interfaces;

/**
 * Created by yuanxin on 2021/9/6.
 */
public class C2 implements A, B {

    public void hello2() {
        B.super.hello();
    }
}
