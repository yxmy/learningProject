package com.yx.mygroup.interfaces;

/**
 * Created by yuanxin on 2021/9/6.
 */
public class HelloImpl implements Hello{
    @Override
    public void sayHello(String name) {
        System.out.println("hello，" + name);
    }
}
