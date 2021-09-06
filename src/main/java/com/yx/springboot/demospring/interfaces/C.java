package com.yx.springboot.demospring.interfaces;

/**
 * Created by yuanxin on 2021/9/6.
 */
public class C extends D {
    @Override
    public void hello() {
        System.out.println("这里必须实现自己的Hello接口，不然编译报错！！！！");
    }
}
